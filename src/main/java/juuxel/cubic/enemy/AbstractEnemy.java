package juuxel.cubic.enemy;

import juuxel.cubic.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.effect.LifeEffect;
import juuxel.cubic.reference.GameValues;

public abstract class AbstractEnemy extends Creature
{
    public boolean sliding = false, slidable = true, edgeMove = true, living = true, ignoreGround = false;
    protected static final int SLIDE_DISTANCE = 32;

    public abstract void move();

    @Override
    public void logic()
    {
        if (!sliding)
            move();
        else
            xSpeed *= 0.97;

        x += xSpeed;
        y += ySpeed;

        if (!ignoreGround && living && y <= GameValues.GROUND)
        {
            y = GameValues.GROUND;
            ySpeed = 0;
        }

        if ((!living || ignoreGround) && y < -20)
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
            if (edgeMove) x = Cubic.game.getWidth() + 10;
            else Cubic.CREATURES.remove(this);
        if (x > Cubic.game.getWidth() + 10)
            if (edgeMove) x = -10;
            else Cubic.CREATURES.remove(this);
    }

    @Override
    public void kill()
    {
        Cubic.score++;
        Cubic.player.ySpeed = 5;

        living = false;
        Cubic.ENEMIES.remove(this);
        ySpeed = -2;

        if (Cubic.ENEMIES.size() == 0)
            Cubic.player.levelUp();

        if (random.nextInt(10) == 1)
            new LifeEffect(x, y);
    }
}
