/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs;

import java.util.Optional;
import java.util.function.Consumer;

public interface Entity
{
    /**
     * @return true if attached
     */
    <T> boolean attachComponent(ComponentId<T> componentId, T component);

    default <T> boolean attachComponent(ComponentId.Producer<T> componentId)
    {
        return attachComponent(componentId, componentId.create());
    }

    <T> Optional<T> getComponent(ComponentId<T> componentId);

    default <T> void onComponent(ComponentId<T> componentId, Consumer<? super T> consumer) {
        getComponent(componentId).ifPresent(consumer);
    }
}
