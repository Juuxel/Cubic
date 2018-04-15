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
