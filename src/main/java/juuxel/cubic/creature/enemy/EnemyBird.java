/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.util.Direction;

public final class EnemyBird extends Enemy
{
    private int poopDelay = 300;
    private final Type type;

    public EnemyBird(Type type)
    {
        y = 200;
        setSprite(type.sprite);
        slidable = false;
        spriteWidth = 48;
        spriteHeight = 48;
        this.type = type;
    }

    public EnemyBird()
    {
        this(Type.BIRD);
    }

    @Override
    public void move()
    {
        if (x == 20)
            direction = Direction.RIGHT;
        else if (x == GameWindow.getWidth() - 20)
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
            spriteWidth = type.poopSize;
            spriteHeight = type.poopSize;
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

    public enum Type
    {
        BIRD(24, Images.bird),
        FLY(16, Images.fly);

        private final int poopSize;
        private final Sprite sprite;

        Type(int poopSize, Sprite sprite)
        {
            this.poopSize = poopSize;
            this.sprite = sprite;
        }
    }
}
