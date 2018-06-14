/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.GameWindow;

public final class EnemyBouncing extends Enemy
{
    public EnemyBouncing()
    {
        x = Math.random() * GameWindow.getWidth();
        y = GameValues.GROUND;
        slidable = false;
        setSprite(Images.bouncingEnemy);
    }

    @Override
    public void move()
    {
        if (Cubic.player.getX() < x)
            xSpeed = Math.max(-1, xSpeed - 1);
        else if (Cubic.player.getX() > x)
            xSpeed = Math.min(1, xSpeed + 1);

        updateSpriteDirection();

        if (ySpeed == 0)
            ySpeed = 2.75;
    }

    @Override
    protected void logic()
    {
        super.logic();

        if (!onGround())
            ySpeed -= 0.1;
    }

    @Override
    protected int getScoreBase()
    {
        return 75;
    }

    @Override
    protected int getScoreAddition()
    {
        return 25;
    }
}
