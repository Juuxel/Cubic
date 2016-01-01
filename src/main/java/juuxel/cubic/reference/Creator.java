package juuxel.cubic.reference;

@FunctionalInterface
public interface Creator<T>
{
    T create();

    default T create(Object... args)
    {
        return create();
    }
}
