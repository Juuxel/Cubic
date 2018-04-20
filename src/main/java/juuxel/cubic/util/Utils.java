/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import juuxel.cubic.Cubic;

/**
 * Includes basic functions for classes to use.
 */
public final class Utils
{
    private Utils() {}

    /**
     * Inverts the given parameter with the game's window height.
     *
     * @param d the number to be inverted
     * @return inverted number
     */
    public static double yOnScreen(double d)
    {
        return Cubic.game.getHeight() - d;
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
}
