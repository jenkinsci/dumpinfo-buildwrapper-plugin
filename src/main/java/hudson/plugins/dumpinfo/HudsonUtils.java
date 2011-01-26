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

package hudson.plugins.dumpinfo;

import hudson.PluginWrapper;
import hudson.PluginManager;
import hudson.model.Computer;
import hudson.model.Hudson;
import hudson.model.JDK;

import java.io.PrintStream;
import java.util.List;

/**
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @version $Id$
 * @since 1.393
 */
public final class HudsonUtils
{
  /**
   * Dump the Hudson slave computer information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   * @param computers
   *          the list of slave computers
   */
  public static void dumpComputers(final PrintStream logger,
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
      logger.println(format(computer));
    }
  }

  /**
   * Dump the Hudson instance information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   */
  public static void dumpHudson(final PrintStream logger)
  {
    if (logger == null)
    {
      return;
    }

    // ---

    logger.println(format(Hudson.getInstance()));
  }

  /**
   * Dump the Hudson JDK information to the job log.
   * 
   * @param logger
   *          the AbstractBuild logger
   * @param jdks
   *          the list of JDK tools
   */
  public static final void dumpJdks(final PrintStream logger,
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
      logger.println(format(jdk));
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
  public static final void dumpPlugins(final PrintStream logger,
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
      logger.println(format(plugin));
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
  public static final void dumpPlugins(final PrintStream logger,
      final PluginManager pluginManager)
  {
    if (pluginManager == null)
    {
      return;
    }

    // ---

    dumpPlugins(logger, pluginManager.getPlugins());
  }

  public static String format(final Computer computer)
  {
    if (computer == null)
    {
      return null;
    }

    // ---

    if (computer.isOnline())
    {
      return Messages.DumpInfo_Computer_Online(computer.getDisplayName(),
          computer.getNumExecutors(), getRootUrl(computer));
    }

    return Messages.DumpInfo_Computer_Offline(computer.getDisplayName(),
        computer.getNumExecutors(), getRootUrl(computer));
  }

  public static String format(final Hudson hudson)
  {
    if (hudson == null)
    {
      return null;
    }

    // ---

    return Messages.DumpInfo_Hudson(hudson.getDisplayName(),
        Hudson.getVersion());
  }

  public static String format(final JDK jdk)
  {
    if (jdk == null)
    {
      return null;
    }

    // ---

    return Messages.DumpInfo_Tool_JDK(jdk.getName(), jdk.getHome());
  }

  public static String format(final PluginWrapper plugin)
  {
    if (plugin == null)
    {
      return null;
    }

    // ---

    return Messages.DumpInfo_Plugin(plugin.getLongName(), plugin.getVersion(),
        plugin.getUrl());
  }

  /**
   * Create a fully qualified URL string to reach a particular Computer
   * definition on the master.
   * 
   * @param computer
   *          the computer to create the URL string for
   * @return the URL string
   */
  public static final String getRootUrl(final Computer computer)
  {
    return Hudson.getInstance().getRootUrl() + "/" + computer.getUrl();
  }

  /**
   * static-only access
   */
  private HudsonUtils()
  {
    // static-only access
  }
}
