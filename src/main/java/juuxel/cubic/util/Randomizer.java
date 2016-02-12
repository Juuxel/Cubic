package juuxel.cubic.util;

import java.util.Random;

public class Randomizer
{
    private static final Random RANDOM = new Random();

    @SafeVarargs
    public static <T> T getRandomObject(T... values)
    {
        return values[RANDOM.nextInt(values.length)];
    }
}
