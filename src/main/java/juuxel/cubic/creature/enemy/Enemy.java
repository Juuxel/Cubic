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
            setXSpeed(getXSpeed() * 0.97);

        if (!isCollisionEnabled() && getY() < -20)
            Cubic.CREATURES.remove(this);

        if (Math.abs(getXSpeed()) < 0.5)
            sliding = false;

        if (slidable)
            Cubic.ENEMIES.forEach(enemy -> {
                if (!(enemy == this) && enemy.slidable
                        && Math.abs(enemy.getX() - getX()) < SLIDE_DISTANCE
                        && Math.abs(enemy.getY() - getY()) < SLIDE_DISTANCE)
                {
                    boolean bool = enemy.getX() > getX();

                    enemy.setXSpeed(getXSpeed() + (bool ? 1.25 : -1.25));
                    setXSpeed(getXSpeed() + (bool ? -1.25 : 1.25));
                    sliding = true;
                }
            });

        if (getX() < -10)
            if (edgeMove) setX(Cubic.game.getWidth() + 10);
            else Cubic.CREATURES.remove(this);
        if (getX() > Cubic.game.getWidth() + 10)
            if (edgeMove) setX(-10);
            else Cubic.CREATURES.remove(this);
    }

    @Override
    public void kill()
    {
        Cubic.score += getScore();
        Cubic.player.setYSpeed(5);

        Cubic.ENEMIES.remove(this);
        setYSpeed(-2);
        setCollisionEnabled(false);

        if (Cubic.ENEMIES.size() == 0)
            Cubic.player.levelUp();

        if (getRandom().nextInt(10) == 1)
        {
            new EffectLife(getX(), getY());
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
