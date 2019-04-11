/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs;

import java.util.Objects;
import java.util.function.Supplier;

/**
 * Identifiers a component.
 *
 * {@link Void} components are used for marking entities.
 *
 * @param <T> the component type
 */
public class ComponentId<T>
{
    private final String id;
    private final Class<T> componentType;

    public ComponentId(String id, Class<T> componentType)
    {
        Objects.requireNonNull(id);
        Objects.requireNonNull(componentType);
        this.id = id;
        this.componentType = componentType;
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(id, componentType);
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ComponentId<?> that = (ComponentId<?>) o;
        return Objects.equals(id, that.id) &&
                Objects.equals(componentType, that.componentType);
    }

    @Override
    public final String toString()
    {
        return componentType.getSimpleName() + " " + id;
    }

    public static final class Producer<T> extends ComponentId<T>
    {
        private final Supplier<? extends T> supplier;

        public Producer(String id, Class<T> componentType, Supplier<? extends T> supplier)
        {
            super(id, componentType);
            Objects.requireNonNull(supplier);
            this.supplier = supplier;
        }

        public T create()
        {
            return supplier.get();
        }

        @Override
        public int hashCode()
        {
            return Objects.hash(super.hashCode(), supplier);
        }

        @Override
        public boolean equals(Object o)
        {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            if (!super.equals(o)) return false;
            Producer<?> producer = (Producer<?>) o;
            return supplier.equals(producer.supplier);
        }
    }
}
