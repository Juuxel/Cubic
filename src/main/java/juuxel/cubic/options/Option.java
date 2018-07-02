/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.options;

import java.awt.Image;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * The {@code Option} class represents options of the game.
 * It has a name and a changeable value.
 *
 * @param <T> the value type
 */
public final class Option<T>
{
    private final String name;
    private T value;
    private final List<ChangeListener<T>> listeners = new ArrayList<>();
    private final Image icon;

    /**
     * {@code true} if this option is a keyboard key and changeable in the option menu.
     * {@code false} otherwise.
     */
    public final boolean isKey;

    /**
     * Constructs a new Option, which is not shown in the option menu.
     *
     * @param name the name
     * @param defaultValue the initial value
     * @see Option#Option(String, Object, boolean, Image)
     */
    Option(String name, T defaultValue)
    {
        this(name, defaultValue, false, null);
    }

    /**
     * Constructs a new Option.
     *
     * @param name the option name
     * @param defaultValue the initial value
     * @param isKey {@code true} this option is a keyboard key
     * @param icon the icon, {@code null} if none
     * @throws IllegalArgumentException if {@code isKey} is true and {@code defaultValue} is not an {@code Integer}
     */
    Option(String name, T defaultValue, boolean isKey, Image icon)
    {
        this.name = name;
        this.value = defaultValue;
        this.isKey = isKey;
        this.icon = icon;

        if (isKey && !(defaultValue instanceof Integer))
            throw new IllegalArgumentException("Key bindings must be instances of Option<Integer>");
    }

    /**
     * Gets the name.
     *
     * @return the name
     */
    public String getName()
    {
        return name;
    }

    /**
     * Gets the current value.
     *
     * @return the value
     */
    public T getValue()
    {
        return value;
    }

    /**
     * Sets the value.
     *
     * @param value the new value
     */
    public void setValue(T value)
    {
        this.value = value;
        listeners.forEach(l -> l.onChange(this));
    }

    /**
     * Gets the option icon, which may be {@code null}.
     * @return the icon
     */
    public Image getIcon()
    {
        return icon;
    }

    /**
     * Adds a change listener, fired when the value is changed.
     *
     * @param listener the listener
     */
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

    /**
     * Listens to changes in an option's value.
     *
     * @param <T> the option's type
     */
    @FunctionalInterface
    public interface ChangeListener<T>
    {
        /**
         * Runs when an option changes if this listener has
         * been added to that option.
         *
         * @param option the option
         */
        void onChange(Option<T> option);
    }
}
