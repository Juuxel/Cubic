/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.fx;

import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.RenderEngine;

public final class EffectNumber extends Effect
{
    private final int number;

    public EffectNumber(double x, double y, int number)
    {
        super(x, y);
        this.number = number;
    }

    @Override
    public void draw(Graphics g)
    {
        String s = Integer.toString(number);

        RenderEngine.drawNumberString(g, s,
                                      (int) x - s.length() * 8 / 2,
                                      (int) yOnScreen(),
                                      ((float) getAge()) / ((float) MAX_AGE));
    }
}
