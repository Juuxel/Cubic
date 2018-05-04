/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import java.util.*;

/**
 * Provides translations for Cubic.
 */
public interface TranslationProvider
{
    /**
     * Gets the translations provided by this provider.
     *
     * @return a List of languages
     */
    List<String> getLanguages();

    /**
     * Returns the name of this provider.
     *
     * @return a String
     */
    String getName();

    /**
     * Returns the translated strings for a language code.
     * Throws a {@link LanguageNotProvidedException} if the language is not provided.
     *
     * @param language the language code
     * @return a Map of translation strings
     * @throws LanguageNotProvidedException if {@code language} is not provided
     */
    Map<String, String> getStringsForLanguage(String language) throws LanguageNotProvidedException;
}
