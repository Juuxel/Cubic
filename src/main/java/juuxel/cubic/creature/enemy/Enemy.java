/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.EffectLife;
import juuxel.cubic.creature.fx.EffectNumber;
import juuxel.cubic.util.Direction;

public abstract class Enemy extends Creature
{
    public boolean sliding = false, slidable = true, edgeMove = true, collidesWithPlayer = true;
    protected static final int SLIDE_DISTANCE = 32;

    public Enemy()
    {
        collidesWithGround = true;
    }

    public abstract void move();

    @Override
    protected void logic()
    {
        if (!sliding)
            move();
        else
            xSpeed *= 0.97;

        if (!collidesWithGround && y < -20)
            Cubic.CREATURES.remove(this);

        if (Math.abs(xSpeed) < 0.5)
            sliding = false;

        if (slidable)
            Cubic.ENEMIES.forEach(enemy -> {
                if (!(enemy == this) && enemy.slidable && Math.abs(enemy.x - x) < SLIDE_DISTANCE && Math.abs(enemy.y - y) < SLIDE_DISTANCE)
                {
                    boolean bool = enemy.x > x;

                    enemy.xSpeed += bool ? 1.25 : -1.25;
                    xSpeed += bool ? -1.25 : 1.25;
                    sliding = true;
                }
            });

        if (x < -10)
            if (edgeMove) x = Cubic.getWidth() + 10;
            else Cubic.CREATURES.remove(this);
        if (x > Cubic.getWidth() + 10)
            if (edgeMove) x = -10;
            else Cubic.CREATURES.remove(this);
    }

    @Override
    public void kill()
    {
        new EffectNumber(x, y, getScore());

        Cubic.player.score += getScore();
        Cubic.player.ySpeed = 5;

        Cubic.ENEMIES.remove(this);
        Cubic.COLLIDING_ENEMIES.remove(this);
        ySpeed = -2;
        collidesWithGround = false;

        if (Cubic.ENEMIES.size() == 0)
            Cubic.player.levelUp();

        if (random.nextInt(10) == 1)
        {
            new EffectLife(x, y);
            Cubic.player.lives++;
        }
    }

    /**
     * Calculates the score that the player gets when they kill this enemy.
     *
     * It gets {@link #getScoreBase the base score} and adds the {@link #getScoreAddition score addition}
     * to it, multiplied by {@link Player#level the player level} - 1
     *
     * @return the calculated score
     * @see #getScoreBase
     * @see #getScoreAddition
     */
    public int getScore()
    {
        return getScoreBase() + getScoreAddition() * (Cubic.player.level - 1);
    }

    /**
     * Gets the base score for killing this enemy.
     *
     * @return the base score
     */
    protected abstract int getScoreBase();

    /**
     * Gets the additional score for killing this enemy. The additional score is multiplied by
     * {@code the player level - 1}.
     *
     * @return the additional score
     */
    protected int getScoreAddition()
    {
        return getScoreBase() / 2;
    }

    /**
     * This method runs when the player collides with this enemy.
     *
     * By default it kills the player if {@code direction} is {@code LEFT} or {@code RIGHT}
     * and kills the enemy if {@code direction} is {@code UP} or {@code DOWN}.
     *
     * @param direction the side of this enemy that the player hit
     */
    public void onCollidedWithPlayer(Direction direction)
    {
        switch (direction)
        {
            case LEFT:
            case RIGHT:
                Cubic.player.kill();
                break;

            case UP:
            case DOWN:
                kill();
                break;
        }
    }
}
