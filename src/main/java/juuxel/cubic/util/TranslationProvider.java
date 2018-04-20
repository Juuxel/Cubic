/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import java.util.List;

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
    List<String> getTranslations();

    /**
     * Returns the name of this provider.
     *
     * @return a String
     */
    String getName();

    /**
     * Returns true if this provider should be loaded from the classpath,
     * false if it should be loaded from the file system.
     *
     * @return a boolean
     */
    default boolean isInternal()
    {
        return false;
    }

    /**
     * Returns the location of this provider's translation.
     *
     * @return a String
     */
    String getLocation();
}
