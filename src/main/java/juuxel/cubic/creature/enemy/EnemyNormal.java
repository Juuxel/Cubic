package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.Graphics;

public class EnemyNormal extends Enemy
{
    public EnemyNormal()
    {
        setX(Math.random() * Cubic.game.getWidth());
        setY(GameValues.GROUND);
    }

    public void draw(Graphics g)
    {
        drawCreature(g, Images.enemy);
    }

    @Override
    public void move()
    {
        if (Cubic.player.getX() < getX())
            setXSpeed(Math.max(-1, getXSpeed() - 1));
        else if (Cubic.player.getX() > getX())
            setXSpeed(Math.min(1, getXSpeed() + 1));
    }

    @Override
    protected int getScoreBase()
    {
        return 50;
    }
}
