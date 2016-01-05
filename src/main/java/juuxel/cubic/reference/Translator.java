package juuxel.cubic.reference;

import juuxel.cubic.api.ITranslationProvider;

import java.util.*;

public class Translator implements ITranslationProvider
{
    private static ResourceBundle bundle;
    private static Locale locale;
    private static List<String> languages;
    private static final List<ITranslationProvider> TRANSLATION_PROVIDERS = new ArrayList<>();

    private Translator()
    {}

    public static void initialize()
    {
        addProvider(new Translator());

        languages = new ArrayList<>();
        loadProviders();

        locale = Locale.getDefault();
        bundle = ResourceBundle.getBundle("assets.lang.lang", locale);
    }

    private static void loadProviders()
    {
        TRANSLATION_PROVIDERS.forEach(provider -> languages.addAll(provider.getTranslations()));
    }

    public static Locale getLocale()
    {
        return locale;
    }

    public static void setLocale(Locale locale)
    {
        Translator.locale = locale;
    }

    public static String translate(String key)
    {
        return bundle.getString(key);
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
