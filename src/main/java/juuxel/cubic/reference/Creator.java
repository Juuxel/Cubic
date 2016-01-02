package juuxel.cubic.reference;

@FunctionalInterface
public interface Creator<T>
{
    T create();
}
