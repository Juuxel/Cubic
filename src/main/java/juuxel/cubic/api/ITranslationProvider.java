package juuxel.cubic.api;

import java.util.List;

/**
 * Provides translations for Cubic.
 * @since 0.3
 */
public interface ITranslationProvider
{
    /**
     * Gets the translations provided by this provider.
     * @return a List of languages
     */
    List<String> getTranslations();
}
