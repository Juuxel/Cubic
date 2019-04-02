/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.util;

import com.google.common.base.Objects;
import com.google.common.collect.*;
import de.tudresden.inf.lat.jsexp.Sexp;
import de.tudresden.inf.lat.jsexp.SexpList;
import juuxel.cubic.render.GameWindow;

import java.awt.Color;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * {@code Utils} contains different utility methods.
 */
public final class Utils
{
    private Utils() {}

    /**
     * Calculates the Y coordinate on the screen from {@code y}.
     *
     * @param y the Y coordinate
     * @return the Y coordinate on the screen
     */
    public static double yOnScreen(double y)
    {
        return GameWindow.getHeight() - y;
    }

    /**
     * Splits the given string with comma (',') as the separator.
     *
     * @param str the string to be split
     * @return the split string
     */
    public static String[] commaSplit(String str)
    {
        return str.split(", *");
    }

    /**
     * Returns true if {@code c} implements {@code i}.
     *
     * @param c the class
     * @param i the interface
     * @return true if {@code c} implements {@code i}
     */
    public static boolean implementsInterface(Class<?> c, Class<?> i)
    {
        for (Class<?> i2 : c.getInterfaces())
        {
            if (i2.equals(i))
                return true;
        }

        return false;
    }

    /**
     * Clamps the number {@code n} between {@code min} and {@code max} (inclusive).
     *
     * @param n the number
     * @param min the minimum
     * @param max the maximum
     * @return a number between {@code min} and {@code max}
     */
    public static float clamp(float n, float min, float max)
    {
        if (n < min)
            return min;
        else if (n > max)
            return max;
        else
            return n;
    }

    /**
     * Returns the {@code color} with the same RGB values, but the alpha value is changed to {@code alpha}.
     *
     * @param color the color
     * @param alpha the alpha
     * @return a new color
     */
    public static Color withAlpha(Color color, float alpha)
    {
        float[] components = color.getRGBComponents(null);

        return new Color(components[0], components[1], components[2], alpha);
    }

    public static <R> R unchecked(ThrowingSupplier<R> supplier)
    {
        try
        {
            return supplier.get();
        }
        catch (Exception e)
        {
            throw Utils.<RuntimeException>unsafeCastException(e);
        }
    }

    public static void unchecked(ThrowingRunnable runnable)
    {
        try
        {
            runnable.run();
        }
        catch (Exception e)
        {
            throw Utils.<RuntimeException>unsafeCastException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static <E extends Exception> E unsafeCastException(Exception e)
    {
        return (E) e;
    }

    public static String sexpToString(Sexp sexp) {
        if (sexp.isAtomic()) {
            return sexp.toString();
        } else {
            return StreamSupport.stream(sexp.spliterator(), false)
                                .map(Utils::sexpToString)
                                .collect(Collectors.joining(","));
        }
    }

    public static ImmutableListMultimap<String, String> sexpToMultimap(Sexp sexp) {
        if (sexp.isAtomic()) {
            return ImmutableListMultimap.of(sexp.toString(), "");
        } else {
            ImmutableListMultimap.Builder<String, String> builder = ImmutableListMultimap.builder();

            for (Sexp child : sexp) {
                Pair<Sexp, ImmutableList<Sexp>> pair = splitSexp(child);
                String key = sexpToString(pair.first);

                pair.second.stream()
                           .map(Utils::sexpToString)
                           .forEach(value -> builder.put(key, value));
            }

            return builder.build();
        }
    }

    public static Pair<Sexp, ImmutableList<Sexp>> splitSexp(Sexp sexp)
    {
        if (sexp.getLength() < 1)
            throw new IllegalArgumentException("Sexp length must be at least 1");

        Iterator<Sexp> iterator = sexp.iterator();
        Sexp head = iterator.next();
        ImmutableList<Sexp> tail = ImmutableList.copyOf(iterator);

        return new Pair<>(head, tail);
    }
}
