/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs.components;

import juuxel.cubic.ecs.ComponentType;
import juuxel.cubic.util.tuple.DoublePair;

public final class Components
{
    private Components()
    {}

    public static final ComponentType<DoublePair> POSITION = new ComponentType<>(DoublePair::new);
    public static final ComponentType<DoublePair> SPEED = new ComponentType<>(DoublePair::new);
    public static final ComponentType<Toggleable> GROUND_COLLISION = new ComponentType<>(Toggleable::new);
    public static final ComponentType<Toggleable> SLIDING = new ComponentType<>(Toggleable::new);
    public static final ComponentType<Void> ENEMY = new ComponentType<>(() -> null);
    public static final ComponentType<Toggleable> ALIVE = new ComponentType<>(Toggleable::new);
}
