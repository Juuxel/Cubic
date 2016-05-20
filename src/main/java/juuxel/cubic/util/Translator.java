package juuxel.cubic.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.*;
import java.util.*;

public class Translator
{
    private static Properties properties;
    private static String language;
    private static List<String> languages;
    private static final List<ITranslationProvider> TRANSLATION_PROVIDERS = new ArrayList<>();

    private Translator()
    {}

    public static void initialize()
    {
        loadFileProviders();

        InputStreamProvider provider = new InputStreamProvider();
        provider.loadTranslation(Translator.class.getResourceAsStream("/data/lang/provider.properties"), "/data/lang/");
        addProvider(provider);

        languages = new ArrayList<>();
        reloadProviders();

        language = getDefault();
        properties = new Properties();

        reloadProperties();
    }

    public static void reloadProviders()
    {
        languages.clear();

        System.out.println("Translation providers:");

        TRANSLATION_PROVIDERS.forEach(provider -> {
            languages.addAll(provider.getTranslations());
            System.out.println(provider.getName());
        });
    }

    public static void reloadProperties()
    {
        try
        {
            properties.load(Translator.class.getResourceAsStream("/data/lang/en_US.lang.properties"));

            ITranslationProvider provider = getProviderForLanguage(language);

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

    public static String getLanguage()
    {
        return language;
    }

    public static void setLanguage(String language)
    {
        Translator.language = language;
    }

    public static void setLanguage(int index)
    {
        setLanguage(languages.get(index));
    }

    public static String translate(String key)
    {
        return properties.getProperty(key);
    }

    public static String format(String key, Object... args)
    {
        return String.format(translate(key), args);
    }

    public static void addProvider(ITranslationProvider provider)
    {
        TRANSLATION_PROVIDERS.add(provider);
    }

    public static List<String> getLanguages()
    {
        return languages;
    }

    public static List<String> getLanguageNames()
    {
        try
        {
            ArrayList<String> output = new ArrayList<>();
            for (String language1 : getLanguages())
            {
                ITranslationProvider provider = getProviderForLanguage(language1);

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

    public static ITranslationProvider getProviderForLanguage(String language)
    {
        for (ITranslationProvider provider : TRANSLATION_PROVIDERS)
        {
            if (provider.getTranslations().contains(language))
                return provider;
        }

        throw new RuntimeException(String.format("Provider for language %s not found!", language));
    }

    public static Locale getLocale()
    {
        return Locale.forLanguageTag(language);
    }

    private static class InputStreamProvider implements ITranslationProvider, IBasicFunctions
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
                translations = Arrays.asList(commaSplit(languageArray));
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
