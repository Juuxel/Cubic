/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.lib.Images;

public final class EnemyBouncing extends EnemyNormal
{
    public EnemyBouncing()
    {
        slidable = false;
        setSprite(Images.bouncingEnemy);
    }

    @Override
    public void move()
    {
        super.move();

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
