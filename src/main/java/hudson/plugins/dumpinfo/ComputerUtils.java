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

import hudson.model.Computer;
import hudson.model.Hudson;

/**
 * Various utility functions for working with {@link hudson.model.Computer}
 * instances.
 * 
 * @author <a href="mailto:jieryn@gmail.com">Jesse Farinacci</a>
 * @since 1.393
 */
public final class ComputerUtils
{
  /**
   * Convenience method to {@link URLEncoder#encode(String)} a Computer URL for
   * proper automatic linkage of console log http URLs in modern installations.
   * 
   * @param computer
   *          the Computer whose URL we are to encode
   * @return the encoded URL
   */
  protected static String encode(final Computer computer)
  {
    if (computer == null)
    {
      return null;
    }

    // ---

    return encode(computer.getUrl());
  }

  /**
   * Convenience method to {@link URLEncoder#encode(String)} a Computer URL for
   * proper automatic linkage of console log http URLs in modern installations.
   * 
   * @param url
   *          the URL to encode
   * @return the encoded URL
   */
  protected static String encode(final String url)
  {
    if (url == null)
    {
      return null;
    }

    // ---

    // temporarily comment out the encoder because it leads to Hudson 404

    // try
    // {
    // return URLEncoder.encode(url, "UTF-8");
    // }
    //
    // catch (final UnsupportedEncodingException e)
    // {
    // /* unlikely, but failed encoding .. fall through */
    // }

    return url;
  }

  /**
   * Create a fully qualified URL string to reach a particular Computer
   * definition on the master.
   * 
   * @param computer
   *          the computer to create the URL string for
   * @return the URL string
   */
  public static String getRootUrl(final Computer computer)
  {
    if (computer == null)
    {
      return null;
    }

    // ---

    return new StringBuilder().append(Hudson.getInstance().getRootUrl())
        .append("/").append(encode(computer)).toString();
  }

  /**
   * static-only access
   */
  private ComputerUtils()
  {
    // static-only access
  }
}
