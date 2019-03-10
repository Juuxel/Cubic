/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs;

import java.util.ArrayList;
import java.util.List;

public class EntityManager
{
    private static final List<Entity> ENTITIES = new ArrayList<>();
    private static final List<EntitySystem> ENTITY_SYSTEMS = new ArrayList<>();

    public static void addEntity(Entity entity) {
        synchronized (ENTITIES) {
            ENTITIES.add(entity);
        }
    }

    public static void removeEntity(Entity entity) {
        synchronized (ENTITIES) {
            ENTITIES.remove(entity);
        }
    }

    public static void addEntitySystem(EntitySystem system) {
        synchronized (ENTITIES) {
            ENTITY_SYSTEMS.add(system);
        }
    }

    public static void tick() {
        ENTITY_SYSTEMS.parallelStream()
                      .forEach(system -> {
                          synchronized (ENTITIES) {
                              system.tick(ENTITIES.stream());
                          }
                      });
    }
}
