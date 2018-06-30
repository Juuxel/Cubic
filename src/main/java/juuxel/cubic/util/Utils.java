/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import juuxel.cubic.render.GameWindow;

/**
 * {@code Utils} contains different utility methods.
 */
public final class Utils
{
    private Utils() {}

    /**
     * Calculates the Y coordinate on the screen from {@code y}.
     *
     * @param y the Y coordinate
     * @return the Y coordinate on the screen
     */
    public static double yOnScreen(double y)
    {
        return GameWindow.getHeight() - y;
    }

    /**
     * Splits the given string with comma (',') as the separator.
     * If a comma is not found, returns <code>new String[] { str }</code>.
     *
     * @param str the string to be split
     * @return the split string
     */
    public static String[] commaSplit(String str)
    {
        if (str.contains(","))
            return str.split(", *");

        return new String[] { str };
    }

    /**
     * Returns true if {@code c} implements {@code i}.
     *
     * @param c the class
     * @param i the interface
     * @return true if {@code c} implements {@code i}
     */
    public static boolean implementsInterface(Class<?> c, Class<?> i)
    {
        for (Class<?> i2 : c.getInterfaces())
        {
            if (i2.equals(i))
                return true;
        }

        return false;
    }
}
