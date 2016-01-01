package juuxel.cubic.enemy;

import juuxel.cubic.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.effect.Life;
import juuxel.cubic.reference.GameValues;

import java.awt.*;

public abstract class Enemy extends Creature
{
    public boolean sliding = false, slidable = true, edgeMove = true, living = true;
    public final Image image;

    public abstract void move();

    public Enemy(Image image)
    {
        this.image = image;
    }

    @Override
    public void logic()
    {
        if (!sliding)
            move();
        else
            xSpeed *= 0.97;

        x += xSpeed;
        y += ySpeed;

        if (living && y <= GameValues.GROUND)
        {
            y = GameValues.GROUND;
            ySpeed = 0;
        }

        if (!living && y < -5)
        {
            Cubic.ENEMIES.remove(this);
            Cubic.CREATURES.remove(this);

            if (Cubic.ENEMIES.size() == 0)
                Cubic.player.levelUp();
        }

        if (Math.abs(xSpeed) < 0.5)
            sliding = false;

        if (slidable)
            Cubic.ENEMIES.forEach(enemy -> {
                if (!(enemy == this) && Math.abs(enemy.x - x) < 32 && Math.abs(enemy.y - y) < 32)
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
        ySpeed = -3;

        if (random.nextInt(10) == 1)
            new Life(x, y);
    }
}
