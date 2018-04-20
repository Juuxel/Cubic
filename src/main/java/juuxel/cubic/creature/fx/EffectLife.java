/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.fx;

import juuxel.cubic.lib.Images;

public final class EffectLife extends Effect
{
    public EffectLife(double x, double y)
    {
        super(x, y);

        setSprite(Images.life);
    }
}
