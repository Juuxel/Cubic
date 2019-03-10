/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs;

import java.util.function.Supplier;

public final class ComponentType<T>
{
    private final Supplier<T> supplier;

    public ComponentType(Supplier<T> supplier)
    {
        this.supplier = supplier;
    }

    public T createComponent()
    {
        return supplier.get();
    }
}
