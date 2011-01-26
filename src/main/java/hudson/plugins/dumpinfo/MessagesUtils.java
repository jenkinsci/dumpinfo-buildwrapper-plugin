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
import hudson.model.Computer;
import hudson.model.Hudson;
import hudson.model.JDK;

/**
 * Various utility functions for working with localized Messages.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @since 1.393
 */
public final class MessagesUtils
{
  /**
   * Format a Computer instance for output.
   * 
   * @param computer
   *          the computer instance to format
   * @return the formatted string
   * 
   * @see Messages#DumpInfo_Computer_Offline(Object, Object, Object)
   * @see Messages#DumpInfo_Computer_Online(Object, Object, Object)
   */
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
          computer.getNumExecutors(), ComputerUtils.getRootUrl(computer));
    }

    return Messages.DumpInfo_Computer_Offline(computer.getDisplayName(),
        computer.getNumExecutors(), ComputerUtils.getRootUrl(computer));
  }

  /**
   * Format a Hudson instance for output.
   * 
   * @param hudson
   *          the Hudson instance to format
   * @return the formatted computer string
   * 
   * @see Messages#DumpInfo_Hudson(Object, Object)
   */
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

  /**
   * Format a JDK instance for output.
   * 
   * @param jdk
   *          the JDK instance to format
   * @return the formatted string
   * 
   * @see Messages#DumpInfo_Tool_JDK(Object, Object)
   */
  public static String format(final JDK jdk)
  {
    if (jdk == null)
    {
      return null;
    }

    // ---

    return Messages.DumpInfo_Tool_JDK(jdk.getName(), jdk.getHome());
  }

  /**
   * Format a PluginWrapper instance for output.
   * 
   * @param pluginWrapper
   *          the plugin wrapper instance to format
   * @return the formatted string
   * 
   * @see Messages#DumpInfo_Plugin(Object, Object, Object)
   */
  public static String format(final PluginWrapper pluginWrapper)
  {
    if (pluginWrapper == null)
    {
      return null;
    }

    // ---

    return Messages.DumpInfo_Plugin(pluginWrapper.getLongName(),
        pluginWrapper.getVersion(), pluginWrapper.getUrl());
  }

  /**
   * static-only access
   */
  private MessagesUtils()
  {
    // static-only access
  }
}
