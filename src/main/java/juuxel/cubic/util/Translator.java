/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

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
    private static final List<LanguageChangeListener> LISTENERS = new ArrayList<>();

    private Translator()
    {}

    /**
     * Initializes Translator.
     */
    public static void init()
    {
        if (hasInit)
            throw new IllegalStateException("Translator has already been initialized.");

        loadFileProviders();

        InputStreamProvider provider = new InputStreamProvider();
        provider.loadTranslation(Translator.class.getResourceAsStream("/data/lang/provider.properties"), "/data/lang/");
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
            languages.addAll(provider.getTranslations());
            System.out.println(provider.getName());
        });
    }

    /**
     * Reloads the localized strings from {@code .properties} files.
     */
    public static void reloadStrings()
    {
        try
        {
            properties.load(Translator.class.getResourceAsStream("/data/lang/en_US.lang.properties"));

            TranslationProvider provider = getProviderForLanguage(language);

            properties.load(provider.isInternal()
                ? Translator.class.getResourceAsStream(provider.getLocation() + String.format("%s.lang.properties", language))
                : Files.newInputStream(Paths.get(provider.getLocation(), String.format("%s.lang.properties", language)))
            );
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
     * @param language the language code
     */
    public static void setLanguage(String language)
    {
        Translator.language = language;
        LISTENERS.forEach(LanguageChangeListener::onLanguageChange);
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
        try
        {
            ArrayList<String> output = new ArrayList<>();
            for (String language1 : getLanguages())
            {
                TranslationProvider provider = getProviderForLanguage(language1);

                Properties properties = new Properties();
                properties.load(provider.isInternal()
                    ? Translator.class.getResourceAsStream(provider.getLocation() + String.format("%s.lang.properties", language1))
                    : Files.newInputStream(Paths.get(provider.getLocation(), String.format("%s.lang.properties", language1)))
                );

                output.add(properties.getProperty("language.name"));
            }

            return output;
        }
        catch (IOException e)
        {
            System.err.println("Error in language names:");
            e.printStackTrace();
            return new ArrayList<>();
        }
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

    private static void loadFileProviders()
    {
        Path path = Paths.get("translations");

        if (Files.exists(path))
        {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path))
            {
                stream.forEach(path1 -> {
                    if (Files.isDirectory(path1))
                    {
                        Path languagePath = Paths.get(path1.toString(), "data", "lang");

                        if (Files.exists(languagePath))
                        {
                            InputStreamProvider provider = new InputStreamProvider();
                            provider.loadTranslation(Paths.get(languagePath.toString(), "provider.properties"));
                            TRANSLATION_PROVIDERS.add(provider);
                        }
                    }
                });
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
    }

    /**
     * Gets the translation provider for {@code language}.
     *
     * @param language the language
     * @return the provider
     * @throws RuntimeException if provider is not found
     */
    public static TranslationProvider getProviderForLanguage(String language)
    {
        for (TranslationProvider provider : TRANSLATION_PROVIDERS)
        {
            if (provider.getTranslations().contains(language))
                return provider;
        }

        throw new RuntimeException(String.format("Provider for language %s not found!", language));
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

    /**
     * Adds a {@link LanguageChangeListener}.
     *
     * @param listener the listener
     */
    public static void addLanguageChangeListener(LanguageChangeListener listener)
    {
        LISTENERS.add(listener);
    }

    private static class InputStreamProvider implements TranslationProvider
    {
        private List<String> translations;
        private String name;
        private boolean internal;
        private String location;

        public void loadTranslation(Path path)
        {
            try
            {
                loadTranslation(Files.newInputStream(path), path.getParent().toString());
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        public void loadTranslation(InputStream stream, String location)
        {
            this.location = location;
            Properties properties = new Properties();

            try
            {
                properties.load(stream);
                String languageArray = properties.getProperty("languages");
                name = properties.getProperty("name");
                translations = Arrays.asList(Utils.commaSplit(languageArray));
                internal = properties.containsKey("internal") ? Boolean.valueOf(properties.getProperty("internal")) : false;
                stream.close();
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }

        @Override
        public List<String> getTranslations()
        {
            return translations;
        }

        @Override
        public String getName()
        {
            return name;
        }

        @Override
        public boolean isInternal()
        {
            return internal;
        }

        @Override
        public String getLocation()
        {
            return location;
        }
    }
}
