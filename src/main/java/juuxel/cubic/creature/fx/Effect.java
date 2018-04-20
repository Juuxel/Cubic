/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.fx;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.sprite.Sprite;

public abstract class Effect extends Creature
{
    public static final int MAX_AGE = 100;

    private int age = MAX_AGE;

    public Effect(double x, double y)
    {
        this.x = x;
        this.y = y;
    }

    @Override
    protected void drawCreature(Graphics g, Sprite sprite)
    {
        int dx = (int) x;
        int dy = (int) yOnScreen();
        g.drawImageWithAlpha(sprite.getImage(this),
                             dx - spriteWidth / 2,
                             dy - spriteHeight / 2,
                             spriteWidth,
                             spriteHeight,
                             ((float) age) / ((float) MAX_AGE));
    }

    @Override
    protected void logic()
    {
        ySpeed = 0.3;

        age--;

        if (age == 0)
            Cubic.CREATURES.remove(this);
    }

    public int getAge()
    {
        return age;
    }
}
