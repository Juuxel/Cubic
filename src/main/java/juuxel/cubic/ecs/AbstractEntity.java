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
    private final Map<ComponentId<?>, Object> componentMap = new HashMap<>();

    protected AbstractEntity()
    {
        attachComponents();
        EntityManager.addEntity(this);
    }

    protected abstract void attachComponents();

    @Override
    public final <T> boolean attachComponent(ComponentId<T> componentId, T component)
    {
        Objects.requireNonNull(componentId);
        if (componentMap.containsKey(componentId))
        {
            return false;
        }

        componentMap.put(componentId, component);
        return true;
    }

    @Override
    @SuppressWarnings("unchecked")
    public final <T> Optional<T> getComponent(ComponentId<T> componentId)
    {
        if (componentMap.containsKey(componentId)) {
            return Optional.of(
                    (T) componentMap.get(componentId)
            );
        }

        return Optional.empty();
    }
}
