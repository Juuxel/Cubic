/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs.systems;

import juuxel.cubic.ecs.EntityManager;
import juuxel.cubic.ecs.EntitySystem;
import juuxel.cubic.ecs.components.Components;

public final class Systems
{
    private Systems() {}

    public static final EntitySystem REMOVE_DEAD = entities -> {
        entities.forEach(entity -> entity.onComponent(Components.ALIVE, comp -> {
            if (!comp.active)
                EntityManager.removeEntity(entity);
        }));
    };
}
