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
import juuxel.cubic.util.Direction;

public final class EnemyFireMonster extends Enemy
{
    private int fireballDelay = 300;
    
    public EnemyFireMonster()
    {
        x = Math.random() * GameWindow.getWidth();
        y = GameValues.GROUND;
        setSprite(Images.fireMonster);
    }

    @Override
    public void move()
    {
        if (Cubic.player.getX() < x)
            xSpeed = Math.max(-1, xSpeed - 1);
        else if (Cubic.player.getX() > x)
            xSpeed = Math.min(1, xSpeed + 1);

        updateSpriteDirection();
        
        fireballDelay--;

        if (fireballDelay == 0)
        {
            fireballDelay = random.nextInt(200) + 350;
            new Fireball();
        }
    }

    @Override
    protected int getScoreBase()
    {
        return 80;
    }

    public final class Fireball extends Enemy
    {
        private final double targetY = EnemyFireMonster.this.y;

        private Fireball()
        {
            x = EnemyFireMonster.this.x;
            y = EnemyFireMonster.this.y;
            edgeMove = false;
            slidable = false;
            setSprite(Images.fireball);
            collidesWithGround = false;
            Cubic.COLLIDING_ENEMIES.add(this);
            direction = EnemyFireMonster.this.direction;
        }

        @Override
        public void move()
        {
            moveX(direction == Direction.RIGHT ? 1.5 : -1.5);
            y = targetY;
            
            if (x < -10 || x > GameWindow.getWidth() + 10)
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
            Cubic.CREATURES.remove(this);
            Cubic.COLLIDING_ENEMIES.remove(this);
        }
    }
}
