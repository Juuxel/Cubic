package juuxel.cubic.options;

/**
 * A KeyBinding is an object representing an action and a key bound to it.
 */
public final class KeyBinding
{
    private int value;

    /**
     * The class constructor.
     *
     * @param initialValue the initial key code of this binding
     */
    public KeyBinding(int initialValue)
    {
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

    @Override
    public String toString()
    {
        return String.valueOf(getValue());
    }
}
