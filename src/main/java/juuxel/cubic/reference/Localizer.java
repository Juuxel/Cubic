package juuxel.cubic.reference;

import java.util.ResourceBundle;

public class Localizer
{
    private static ResourceBundle bundle;

    public static void initialize()
    {
        bundle = ResourceBundle.getBundle("assets.lang.lang");
    }

    public static String localize(String key)
    {
        return bundle.getString(key);
    }

    public static String format(String key, Object... args)
    {
        return String.format(localize(key), args);
    }
}
