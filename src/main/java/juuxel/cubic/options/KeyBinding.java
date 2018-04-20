/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.options;

import java.util.*;

/**
 * A KeyBinding is an object representing an action and a key bound to it.
 */
public final class KeyBinding
{
    private int value;
    private final List<ChangeListener> changeListeners = new ArrayList<>();
    private final String name;

    /**
     * The class constructor.
     *
     * @param name the name of this key binding
     * @param initialValue the initial key code of this binding
     */
    public KeyBinding(String name, int initialValue)
    {
        this.name = name;
        value = initialValue;
    }

    /**
     * Sets the key code of this binding.
     *
     * @param value the new key code
     */
    public void setValue(int value)
    {
        this.value = value;
        changeListeners.forEach(l -> l.handleChange(this));
    }

    /**
     * Gets the key code of this binding.
     *
     * @return the key code
     */
    public int getValue()
    {
        return value;
    }

    public void addChangeListener(ChangeListener l)
    {
        changeListeners.add(l);
    }

    @Override
    public String toString()
    {
        return String.valueOf(getValue());
    }

    public String getName()
    {
        return name;
    }

    @FunctionalInterface
    public interface ChangeListener
    {
        void handleChange(KeyBinding b);
    }
}
