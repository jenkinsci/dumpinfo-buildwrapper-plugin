/*
 * The MIT License
 * 
 * Copyright (c) 2011, Jesse Farinacci
 * 
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * 
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 * 
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */

package org.jvnet.hudson.plugins;

import hudson.Extension;
import hudson.Launcher;
import hudson.PluginWrapper;
import hudson.PluginManager;
import hudson.model.BuildListener;
import hudson.model.AbstractBuild;
import hudson.model.AbstractProject;
import hudson.model.Computer;
import hudson.model.Hudson;
import hudson.model.JDK;
import hudson.tasks.BuildWrapper;
import hudson.tasks.BuildWrapperDescriptor;

import java.io.IOException;
import java.io.PrintStream;
import java.util.List;

import org.kohsuke.stapler.DataBoundConstructor;

/**
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @version $Id$
 * @since 1.393
 */
public final class DumpInfoBuildWrapper extends BuildWrapper
{
  /**
   * Plugin marker for BuildWrapper.
   */
  @Extension
  public static class DescriptorImpl extends BuildWrapperDescriptor
  {
    @Override
    public String getDisplayName()
    {
      return DumpInfoBuildWrapper.class.getName();
    }

    @Override
    public boolean isApplicable(final AbstractProject<?, ?> item)
    {
      return true;
    }
  }

  /**
   * Dump the Hudson slave computer information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   * @param computers
   *          the list of slave computers
   */
  private static final void dumpComputers(final PrintStream logger,
      final Computer[] computers)
  {
    if (logger == null)
    {
      return;
    }

    if (computers == null)
    {
      return;
    }

    // ---

    for (final Computer computer : computers)
    {
      if (computer.isOnline())
      {
        logger.println(Messages.DumpInfo_Computer_Online(
            computer.getDisplayName(), computer.getNumExecutors(),
            getRootUrl(computer)));
      }

      else
      {
        logger.println(Messages.DumpInfo_Computer_Offline(
            computer.getDisplayName(), computer.getNumExecutors(),
            getRootUrl(computer)));
      }
    }
  }

  /**
   * Dump the Hudson instance information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   */
  private static void dumpHudson(final PrintStream logger)
  {
    if (logger == null)
    {
      return;
    }

    // ---

    final Hudson hudson = Hudson.getInstance();
    logger.println(Messages.DumpInfo_Hudson(hudson.getDisplayName(),
        Hudson.getVersion()));
  }

  /**
   * Dump the Hudson JDK information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   * @param jdks
   *          the list of JDK tools
   */
  private static final void dumpJdks(final PrintStream logger,
      final List<JDK> jdks)
  {
    if (logger == null)
    {
      return;
    }

    if (jdks == null)
    {
      return;
    }

    // ---

    for (final JDK jdk : jdks)
    {
      logger.println(Messages.DumpInfo_Tool_JDK(jdk.getName(), jdk.getHome()));
    }
  }

  /**
   * Dump the Hudson plugin information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   * @param plugins
   *          the list of plugins
   */
  private static final void dumpPlugins(final PrintStream logger,
      final List<PluginWrapper> plugins)
  {
    if (logger == null)
    {
      return;
    }

    if (plugins == null)
    {
      return;
    }

    // ---

    for (final PluginWrapper plugin : plugins)
    {
      logger.println(Messages.DumpInfo_Plugin(plugin.getLongName(),
          plugin.getVersion(), plugin.getUrl()));
    }
  }

  /**
   * Dump the Hudson plugin information to the job log.
   * 
   * @param logger
   *          the job logger
   * @param pluginManager
   *          the plugin manager
   */
  private static final void dumpPlugins(final PrintStream logger,
      final PluginManager pluginManager)
  {
    if (pluginManager == null)
    {
      return;
    }

    // ---

    dumpPlugins(logger, pluginManager.getPlugins());
  }

  /**
   * Create a fully qualified URL string to reach a particular Computer
   * definition on the master.
   * 
   * @param computer
   *          the computer to create the URL string for
   * @return the URL string
   */
  private static final String getRootUrl(final Computer computer)
  {
    return Hudson.getInstance().getRootUrl() + "/" + computer.getUrl();
  }

  /**
   * Whether or not to dump information about Hudson slave computers.
   * 
   * @see Hudson#getComputers()
   */
  private final boolean dumpComputers;

  /**
   * Whether or not to dump information about Hudson JDK tools.
   * 
   * @see Hudson#getJDKs()
   */
  private final boolean dumpJdks;

  /**
   * Whether or not to dump information about Hudson plugins.
   * 
   * @see Hudson#getPluginManager()
   * @see PluginManager#getPlugins()
   */
  private final boolean dumpPlugins;

  @DataBoundConstructor
  public DumpInfoBuildWrapper(final boolean dumpComputers,
      final boolean dumpJdks, final boolean dumpPlugins)
  {
    this.dumpComputers = dumpComputers;
    this.dumpJdks = dumpJdks;
    this.dumpPlugins = dumpPlugins;
  }

  /**
   * Get whether or not to dump information about Hudson slave computers.
   * 
   * @return whether or not to dump information about Hudson slave computers
   */
  public boolean isDumpComputers()
  {
    return dumpComputers;
  }

  /**
   * Get whether or not to dump information about Hudson JDK tools.
   * 
   * @return whether or not to dump information about Hudson JDK tools
   */
  public boolean isDumpJdks()
  {
    return dumpJdks;
  }

  /**
   * Get whether or not to dump information about Hudson plugins.
   * 
   * @return whether or not to dump information about Hudson plugins
   */
  public boolean isDumpPlugins()
  {
    return dumpPlugins;
  }

  @Override
  public BuildWrapper.Environment setUp(
      @SuppressWarnings("rawtypes") final AbstractBuild build,
      final Launcher launcher, final BuildListener listener)
      throws IOException, InterruptedException
  {
    final Hudson hudson = Hudson.getInstance();
    final PrintStream logger = listener.getLogger();

    // ---

    dumpHudson(logger);

    if (dumpComputers)
    {
      dumpComputers(logger, hudson.getComputers());
    }

    if (dumpJdks)
    {
      dumpJdks(logger, hudson.getJDKs());
    }

    if (dumpPlugins)
    {
      dumpPlugins(logger, hudson.getPluginManager());
    }

    // ---

    return new Environment()
    {
      /* empty implementation */
    };
  }
}
