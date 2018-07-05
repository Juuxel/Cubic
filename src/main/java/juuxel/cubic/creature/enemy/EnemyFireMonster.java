/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.Creature;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.Sounds;

import java.awt.geom.AffineTransform;
import java.util.concurrent.ThreadLocalRandom;

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
            Sounds.FIREBALL.start();
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
        private int smokeDelay = 0;

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

            smokeDelay--;

            if (smokeDelay <= 0)
            {
                smokeDelay = 40;
                new Smoke(x, y);
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

    private static class Smoke extends Creature
    {
        private static final float MAX_AGE = 100f;

        private float age = MAX_AGE;
        private final float size = 0.5f + ThreadLocalRandom.current().nextFloat();

        private Smoke(double x, double y)
        {
            this.x = x;
            this.y = y;
            setSprite(Images.smoke);
        }

        @Override
        protected void drawCreature(Graphics g, Sprite sprite)
        {
            int dx = (int) x;
            int dy = (int) yOnScreen();
            int width = (int) (spriteWidth * age / MAX_AGE * size);
            int height = (int) (spriteHeight * age / MAX_AGE * size);

            var transform = g.getGraphics2D().getTransform();
            g.getGraphics2D().setTransform(
                    AffineTransform.getRotateInstance(
                            Math.toRadians(age / MAX_AGE * 360.0),
                            dx, dy
                    ));

            g.drawImageWithAlpha(sprite.getImage(this),
                                 dx - width / 2,
                                 dy - height / 2,
                                 width, height,
                                 age / MAX_AGE * 0.85f);
            g.getGraphics2D().setTransform(transform);
        }

        @Override
        protected void logic()
        {
            age--;

            if (age == 0)
                Cubic.CREATURES.remove(this);
        }
    }
}
