/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.ecs.components;

public final class CollisionBoxComponent
{
    public final int width;
    public final int height;

    public CollisionBoxComponent(int width, int height)
    {
        this.width = width;
        this.height = height;
    }
}
