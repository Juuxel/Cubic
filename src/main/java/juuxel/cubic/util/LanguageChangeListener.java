/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

/**
 * This interface is used to listen to language changes.
 *
 * @see Translator#addLanguageChangeListener(LanguageChangeListener)
 */
public interface LanguageChangeListener
{
    /**
     * This method is called when the game language changes.
     */
    void onLanguageChange();
}
