package juuxel.cubic.util;

@FunctionalInterface
public interface Creator<T>
{
    T create();
}
