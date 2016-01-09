package juuxel.cubic.options;

public class KeyBinding
{
    private final String name;
    private int value;

    public KeyBinding(String name, int initialValue)
    {
        this.name = name;
        value = initialValue;
    }

    public void setValue(int value)
    {
        this.value = value;
    }

    public int getValue()
    {
        return value;
    }

    public String getName()
    {
        return name;
    }
}
