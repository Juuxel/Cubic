package juuxel.cubic.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

public class Randomizer
{
    private static final Random RANDOM = new Random();

    @SafeVarargs
    public static <T> T getRandomObject(T... values)
    {
        return values[RANDOM.nextInt(values.length)];
    }

    public static <T> T getRandomObject(Collection<T> collection)
    {
        List<T> list = new ArrayList<>(collection);

        return list.get(RANDOM.nextInt(list.size()));
    }
}
