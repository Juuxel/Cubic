package juuxel.cubic.creature.enemy;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.EffectLife;

public abstract class Enemy extends Creature
{
    public boolean sliding = false, slidable = true, edgeMove = true;
    protected static final int SLIDE_DISTANCE = 32;

    public Enemy()
    {
        setCollisionEnabled(true);
    }

    public abstract void move();

    @Override
    protected void logic()
    {
        if (!sliding)
            move();
        else
            xSpeed *= 0.97;

        if (!isCollisionEnabled() && y < -20)
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
        Cubic.score += getScore();
        Cubic.player.ySpeed = 5;

        Cubic.ENEMIES.remove(this);
        ySpeed = -2;
        setCollisionEnabled(false);

        if (Cubic.ENEMIES.size() == 0)
            Cubic.player.levelUp();

        if (random.nextInt(10) == 1)
        {
            new EffectLife(x, y);
            Cubic.lives++;
        }
    }

    public int getScore()
    {
        return getScoreBase() + getScoreAddition() * (Cubic.level - 1);
    }

    protected abstract int getScoreBase();

    protected int getScoreAddition()
    {
        return getScoreBase();
    }
}
