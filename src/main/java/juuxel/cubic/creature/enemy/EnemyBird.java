/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.Randomizer;

public final class EnemyBird extends Enemy
{
    private int poopDelay = 300;

    public EnemyBird()
    {
        y = 200;
        setSprite(Images.bird);
        slidable = false;
        spriteWidth = 48;
        spriteHeight = 48;
    }

    @Override
    public void move()
    {
        if (x == 20)
            direction = Direction.RIGHT;
        else if (x == Cubic.game.getWidth() - 20)
            direction = Direction.LEFT;

        moveX(direction == Direction.RIGHT ? 1 : -1);

        poopDelay--;

        if (poopDelay == 0)
        {
            poopDelay = random.nextInt(200) + 350;
            new Poop();
        }
    }

    @Override
    protected int getScoreBase()
    {
        return 75;
    }

    public final class Poop extends Enemy
    {
        private Poop()
        {
            x = EnemyBird.this.x + (EnemyBird.this.direction == Direction.RIGHT ? 10 : -10);
            y = EnemyBird.this.y - 32;
            ySpeed = -1;
            slidable = false;
            spriteWidth = 24;
            spriteHeight = 24;
            setSprite(Images.birdPoop);
            collidesWithGround = false;
            Cubic.COLLIDING_ENEMIES.add(this);
        }

        @Override
        public void move()
        {
            if (y < 0)
            {
                Cubic.COLLIDING_ENEMIES.remove(this);
                Cubic.CREATURES.remove(this);
            }
        }

        @Override
        protected int getScoreBase()
        {
            return 0;
        }

        @Override
        public void onCollidedWithPlayer(Direction direction)
        {
            Cubic.player.kill();
            Cubic.COLLIDING_ENEMIES.remove(this);
        }
    }
}
