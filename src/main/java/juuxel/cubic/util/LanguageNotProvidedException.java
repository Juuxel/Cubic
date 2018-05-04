/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

/**
 * This exception is thrown when a {@link TranslationProvider} is not provided for a specific language.
 *
 * @see juuxel.cubic.util.TranslationProvider#getStringsForLanguage(String) an example of this exception
 */
public final class LanguageNotProvidedException extends RuntimeException
{
    /**
     * Initializes this class with an error message.
     *
     * @param message the message
     */
    public LanguageNotProvidedException(String message)
    {
        super(message);
    }
}
