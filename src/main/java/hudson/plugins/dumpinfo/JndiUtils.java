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

import java.util.SortedMap;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.naming.Binding;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;

/**
 * Various utility functions for working with <a
 * href="http://en.wikipedia.org/wiki/Java_Naming_and_Directory_Interface"
 * >JNDI</a>.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @since 1.2
 */
public final class JndiUtils
{
  private static final Logger LOG = Logger.getLogger(JndiUtils.class.getName());

  /**
   * Get a sorted map of JNDI bindings.
   * 
   * @return the sorted JNDI bindings
   */
  public static SortedMap<String, String> getJndiBindings()
  {
    final SortedMap<String, String> map = new TreeMap<String, String>();

    try
    {
      Context ctx = (Context) new InitialContext().lookup("java:comp/env");
      NamingEnumeration list = ctx.listBindings("");
      while (list.hasMore())
      {
        Binding item = (Binding) list.next();
        map.put(item.getClassName(), item.getName());
        // Object o = item.getObject();
        // if (o instanceof javax.naming.Context)
        // {
        // listContext((Context) o, indent + " ");
        // }
      }

    }

    catch (final NamingException e)
    {
      LOG.log(Level.WARNING, e.getMessage(), e);
    }

    return map;
  }

  /**
   * Static-only access.
   */
  private JndiUtils()
  {
    /* static-only access */
  }
}
