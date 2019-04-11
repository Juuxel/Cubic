/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs.components;

import juuxel.cubic.ecs.ComponentId;
import juuxel.cubic.util.tuple.DoublePair;

public final class Components
{
    private Components()
    {}

    public static final ComponentId.Producer<DoublePair> POSITION = new ComponentId.Producer<>("position", DoublePair.class, DoublePair::new);
    public static final ComponentId.Producer<DoublePair> SPEED = new ComponentId.Producer<>("speed", DoublePair.class, DoublePair::new);
    public static final ComponentId.Producer<Toggleable> GROUND_COLLISION = new ComponentId.Producer<>("ground collision", Toggleable.class, Toggleable::new);
    public static final ComponentId.Producer<Toggleable> SLIDING = new ComponentId.Producer<>("sliding", Toggleable.class, Toggleable::new);
    public static final ComponentId.Producer<Void> ENEMY = new ComponentId.Producer<>("enemy", Void.class, () -> null);
    public static final ComponentId.Producer<Toggleable> ALIVE = new ComponentId.Producer<>("alive", Toggleable.class, Toggleable::new);
    public static final ComponentId<CollisionBoxComponent> COLLISION_BOX = new ComponentId<>("collision box", CollisionBoxComponent.class);
}
