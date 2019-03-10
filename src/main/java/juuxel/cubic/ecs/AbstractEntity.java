/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs;

import java.util.*;

public abstract class AbstractEntity implements Entity
{
    private final Map<ComponentType<?>, Object> componentMap = new HashMap<>();

    protected AbstractEntity()
    {
        attachComponents();
        EntityManager.addEntity(this);
    }

    protected abstract void attachComponents();

    @Override
    public final boolean attachComponent(ComponentType<?> componentType)
    {
        Objects.requireNonNull(componentType);

        if (!componentMap.containsKey(componentType))
        {
            componentMap.put(componentType, componentType.createComponent());
            return true;
        }

        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> Optional<T> getComponent(ComponentType<T> componentType)
    {
        if (componentMap.containsKey(componentType)) {
            return Optional.of(
                    (T) componentMap.get(componentType)
            );
        }

        return Optional.empty();
    }
}
