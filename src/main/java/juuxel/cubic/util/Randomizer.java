package juuxel.cubic.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Random;

/**
 * This class provides randomizing on collections and arrays.
 */
public class Randomizer
{
    private static final Random RANDOM = new Random();

    /**
     * Gets a random object of an array.
     *
     * @param values the array
     * @param <T> the type
     * @return a random object
     */
    @SafeVarargs
    public static <T> T getRandomObject(T... values)
    {
        return values[RANDOM.nextInt(values.length)];
    }

    /**
     * Gets a random object of a collection.
     *
     * @param collection the collection
     * @param <T> the type
     * @return a random object
     */
    public static <T> T getRandomObject(Collection<T> collection)
    {
        List<T> list = new ArrayList<>(collection);

        return list.get(RANDOM.nextInt(list.size()));
    }
}
