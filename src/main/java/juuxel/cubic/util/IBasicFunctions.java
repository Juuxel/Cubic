package juuxel.cubic.util;

import juuxel.cubic.Cubic;

/**
 * Includes basic functions for classes to implement.
 */
public interface IBasicFunctions
{
    /**
     * IBasicFunctions object for usage in static methods.
     */
    IBasicFunctions DEFAULT = new DefaultImpl();

    /**
     * Inverts the given parameter with the game's window height.
     *
     * @param d the number to be inverted
     * @return inverted number
     */
    default double calculateY(double d)
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
    default String[] commaSplit(String str)
    {
        if (str.contains(","))
            return str.split(", *");

        return new String[] { str };
    }

    // PLEASE DON'T TOUCH
    class DefaultImpl implements IBasicFunctions
    {
        private DefaultImpl()
        {}
    }
}
