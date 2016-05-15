package juuxel.cubic.util;

public final class Strings
{
    private Strings()
    {
        throw new RuntimeException("Strings should not be initialized.");
    }

    public static String[] commaSplit(String str)
    {
        if (str.contains(","))
            return str.split(",");

        return new String[] { str };
    }
}
