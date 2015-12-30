package juuxel.cubic.enemy;

import juuxel.cubic.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.effect.DeathTrail;
import juuxel.cubic.effect.LevelUpEffect;
import juuxel.cubic.effect.Life;

import java.awt.*;

public abstract class AbstractEnemy extends Creature
{
    public boolean sliding = false, slidable = true, edgeMove = true;
    public final Image image;

    public abstract void move();

    public AbstractEnemy(Image image)
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

        Cubic.ENEMIES.remove(this);
        Cubic.CREATURES.remove(this);
        new DeathTrail(x, y, image);

        if (Cubic.ENEMIES.size() == 0)
        {
            Cubic.level++;
            Cubic.lives++;

            new LevelUpEffect(Cubic.player.x, Cubic.player.y);

            for (int i = 0; i < Cubic.level; i++) Cubic.ENEMIES.add(new Enemy());

            if (Cubic.level % 5 == 0)
                Cubic.ENEMIES.add(new BouncingEnemy());
        }

        if (random.nextInt(10) == 1)
            new Life(x, y);
    }
}
