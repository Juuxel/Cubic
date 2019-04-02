/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import com.google.common.collect.ListMultimap;
import com.google.common.collect.Multimap;
import de.tudresden.inf.lat.jsexp.SexpFactory;
import juuxel.cubic.event.EventBus;
import juuxel.cubic.event.LanguageChangeEvent;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

/**
 * Gets localized strings for localization keys.
 */
public final class Translator
{
    private static Properties properties;
    private static String language;
    private static List<String> languages;
    private static boolean hasInit = false;
    private static final List<TranslationProvider> TRANSLATION_PROVIDERS = new ArrayList<>();

    private Translator()
    {}

    /**
     * Initializes Translator.
     */
    public static void init()
    {
        if (hasInit)
            throw new IllegalStateException("Translator has already been initialized.");

        InputStreamProvider provider = new InputStreamProvider();
        provider.loadTranslation(Translator.class.getResourceAsStream("/data/lang/provider.sexpr"), "/data/lang/");
        addProvider(provider);

        languages = new ArrayList<>();
        reloadProviders();

        language = getDefault();
        properties = new Properties();

        reloadStrings();

        hasInit = true;
    }

    private static void reloadProviders()
    {
        languages.clear();

        System.out.println("Translation providers:");

        TRANSLATION_PROVIDERS.forEach(provider -> {
            languages.addAll(provider.getLanguages());
            System.out.println(provider.getName());
        });
    }

    /**
     * Reloads the localized strings from all providers.
     */
    public static void reloadStrings()
    {
        try
        {
            properties.load(Translator.class.getResourceAsStream("/data/lang/en_US.lang.properties"));

            TranslationProvider provider = getProviderForLanguage(language);

            properties.putAll(provider.getStringsForLanguage(language));
        }
        catch (IOException e)
        {
            System.err.println("Language properties couldn't be loaded...");
            e.printStackTrace();
        }
    }

    private static String getDefault()
    {
        return Locale.getDefault().toLanguageTag().replace('-', '_');
    }

    /**
     * Gets the current game language.
     *
     * @return the language code
     */
    public static String getLanguage()
    {
        return language;
    }

    /**
     * Sets the current game language.
     *
     * @param newLanguage the language code
     */
    public static void setLanguage(String newLanguage)
    {
        EventBus.post(LanguageChangeEvent.INSTANCE);
        language = newLanguage;
    }

    /**
     * Sets the current game language by the index in {@link #getLanguages() the language list}.
     *
     * @param index the language index
     */
    public static void setLanguage(int index)
    {
        setLanguage(languages.get(index));
    }

    /**
     * Gets a translated string by {@code key}.
     *
     * @param key the translation key
     * @return the translated string
     */
    public static String translate(String key)
    {
        return properties.getProperty(key);
    }

    /**
     * Gets a translated string by {@code key}, formatted using {@code args}.
     *
     * @param key the translation key
     * @param args the format arguments
     * @return the translated string
     */
    public static String format(String key, Object... args)
    {
        return String.format(translate(key), args);
    }

    /**
     * Adds a {@link TranslationProvider}.
     *
     * @param provider the translation provider
     */
    public static void addProvider(TranslationProvider provider)
    {
        TRANSLATION_PROVIDERS.add(provider);
    }

    /**
     * Gets the languages registered in the game.
     *
     * @return a list of languages
     */
    public static List<String> getLanguages()
    {
        return languages;
    }

    /**
     * Gets the languages registered in the game, converted to the language names.
     *
     * @return a list of languages
     */
    public static List<String> getLanguageNames()
    {
        ArrayList<String> output = new ArrayList<>();
        for (String language1 : getLanguages())
        {
            TranslationProvider provider = getProviderForLanguage(language1);

            output.add(provider.getStringsForLanguage(language1).get("language.name"));
        }

        return output;
    }

    /**
     * Gets the current language's index in the language list.
     *
     * @return an index
     * @see #getLanguage()
     */
    public static int getLanguageIndex()
    {
        return languages.indexOf(language);
    }

    /**
     * Gets the translation provider for {@code language}.
     *
     * @param language the language
     * @return the provider
     * @throws LanguageNotProvidedException if provider is not found
     */
    public static TranslationProvider getProviderForLanguage(String language)
    {
        for (TranslationProvider provider : TRANSLATION_PROVIDERS)
        {
            if (provider.getLanguages().contains(language))
                return provider;
        }

        throw new LanguageNotProvidedException(String.format("Provider for language %s not found!", language));
    }

    /**
     * Gets the {@code Locale} object of the current language.
     *
     * @return the Locale
     */
    public static Locale getLocale()
    {
        return Locale.forLanguageTag(language);
    }

    private static class InputStreamProvider implements TranslationProvider
    {
        private List<String> languages;
        private String name;
        private boolean internal;
        private String location;

        public void loadTranslation(InputStream stream, String location)
        {
            this.location = location;

            try
            {
                ListMultimap<String, String> props = Utils.sexpToMultimap(SexpFactory.parse(stream));
                name = props.get("name").get(0);
                languages = props.get("languages");
                internal = props.containsKey("internal") ? Boolean.valueOf(props.get("internal").get(0)) : false;
                stream.close();
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public List<String> getLanguages()
        {
            return languages;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public Map<String, String> getStringsForLanguage(String language)
        {
            if (!languages.contains(language))
                throw new LanguageNotProvidedException("Language " + language + " not provided!");

            try
            {
                Properties props = new Properties();

                props.load(internal
                                   ? Translator.class.getResourceAsStream(location + String.format("%s.lang.properties", language))
                                   : Files.newInputStream(Paths.get(location, String.format("%s.lang.properties", language)))
                );

                Map<String, String> map = new HashMap<>();

                for (Object key : props.keySet())
                {
                    map.put((String) key, props.getProperty((String) key));
                }

                return map;
            }
            catch (IOException e)
            {
                throw new RuntimeException(e);
            }
        }
    }
}
