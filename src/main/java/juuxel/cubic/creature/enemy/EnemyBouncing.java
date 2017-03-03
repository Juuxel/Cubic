package juuxel.cubic.creature.enemy;

import juuxel.cubic.lib.Images;
import juuxel.cubic.render.Graphics;

public class EnemyBouncing extends EnemyNormal
{
    public EnemyBouncing()
    {
        slidable = false;
    }

    @Override
    public void move()
    {
        super.move();

        if (getYSpeed() == 0)
            setYSpeed(2.75);
    }

    @Override
    protected void logic()
    {
        super.logic();

        if (!onGround())
            setYSpeed(getYSpeed() - 0.1); // TODO Figure out a way to do -=
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.bouncingEnemy);
    }

    @Override
    protected int getScoreBase()
    {
        return 75;
    }

    @Override
    protected int getScoreAddition()
    {
        return 25;
    }
}
