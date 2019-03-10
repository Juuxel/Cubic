/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs.components;

import juuxel.cubic.ecs.ComponentType;

public final class CollisionBoxComponent
{
    public final int width;
    public final int height;

    public CollisionBoxComponent(int width, int height)
    {
        this.width = width;
        this.height = height;
    }

    public static ComponentType<CollisionBoxComponent> createComponentType(int width, int height)
    {
        return new ComponentType<>(() -> new CollisionBoxComponent(width, height));
    }
}
