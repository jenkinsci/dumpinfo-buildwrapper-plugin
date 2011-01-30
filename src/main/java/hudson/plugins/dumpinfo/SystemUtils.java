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

import java.util.Properties;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * Various utility functions for working with {@link java.lang.System}.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @since 1.1
 */
public final class SystemUtils
{
  /**
   * Get a sorted map of system environment variables.
   * 
   * @return the sorted environment variables
   * 
   * @see System#getenv()
   */
  public static SortedMap<String, String> getEnvironmentVariables()
  {
    final SortedMap<String, String> map = new TreeMap<String, String>();

    for (final String key : new TreeSet<String>(System.getenv().keySet()))
    {
      map.put(key, System.getenv(key));
    }

    return map;
  }

  /**
   * Get a sorted map of system properties.
   * 
   * @return the sorted system properties
   * 
   * @See {@link System#getProperties()}
   */
  public static SortedMap<String, String> getSystemProperties()
  {
    final SortedMap<String, String> map = new TreeMap<String, String>();

    final Properties properties = System.getProperties();
    for (final Object key : properties.keySet())
    {
      map.put(key.toString(), properties.getProperty(key.toString()).toString());
    }

    return map;
  }

  /**
   * Static-only access.
   */
  private SystemUtils()
  {
    /* static-only access */
  }
}
