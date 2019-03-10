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
    boolean attachComponent(ComponentType<?> componentType);

    <T> Optional<T> getComponent(ComponentType<T> componentType);

    default <T> void onComponent(ComponentType<T> componentType, Consumer<? super T> consumer) {
        getComponent(componentType).ifPresent(consumer);
    }
}
