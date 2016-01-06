package juuxel.cubic.reference;

import juuxel.cubic.api.ITranslationProvider;

import java.io.IOException;
import java.util.*;

public class Translator implements ITranslationProvider
{
    private static Properties properties;
    private static String language;
    private static List<String> languages;
    private static final List<ITranslationProvider> TRANSLATION_PROVIDERS = new ArrayList<>();

    private Translator()
    {}

    public static void initialize()
    {
        addProvider(new Translator());

        languages = new ArrayList<>();
        reloadProviders();

        language = getDefault();
        properties = new Properties();

        reloadProperties();
    }

    public static void reloadProviders()
    {
        languages.clear();
        TRANSLATION_PROVIDERS.forEach(provider -> languages.addAll(provider.getTranslations()));
    }

    public static void reloadProperties()
    {
        try
        {
            properties.load(Translator.class.getResourceAsStream("/assets/lang/en_US.lang.properties"));
            properties.load(Translator.class.getResourceAsStream(String.format("/assets/lang/%s.lang.properties", language)));
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

    @Override
    public List<String> getTranslations()
    {
        return new ArrayList<>(Arrays.asList(Reference.LANGUAGES));
    }
}
