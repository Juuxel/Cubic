/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.options;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class Option<T>
{
    private final String name;
    private T value;
    public final boolean showInGui;
    private final List<ChangeListener<T>> listeners = new ArrayList<>();

    Option(String name, T defaultValue)
    {
        this(name, defaultValue, false);
    }

    Option(String name, T defaultValue, boolean showInGui)
    {
        this.name = name;
        this.value = defaultValue;
        this.showInGui = showInGui;
    }

    public String getName()
    {
        return name;
    }

    public T getValue()
    {
        return value;
    }

    public void setValue(T value)
    {
        this.value = value;
        listeners.forEach(l -> l.onChange(this));
    }

    public void addChangeListener(ChangeListener<T> listener)
    {
        listeners.add(listener);
    }

    @Override
    public boolean equals(Object obj)
    {
        if (!(obj instanceof Option<?>))
            return false;

        var other = (Option<?>) obj;

        return Objects.equals(name, other.name) && Objects.equals(value, other.value);
    }

    @Override
    public int hashCode()
    {
        return 47 + name.hashCode() + value.hashCode();
    }

    @FunctionalInterface
    public interface ChangeListener<T>
    {
        void onChange(Option<T> option);
    }
}
