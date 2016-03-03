package juuxel.cubic.creature.enemy;

import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.render.Graphics;

public class CreatureEnemyBouncing extends CreatureEnemyNormal
{
    @Override
    public void move()
    {
        super.move();

        if (ySpeed == 0)
            ySpeed = 2.75;
    }

    @Override
    public void logic()
    {
        super.logic();

        if (!(y <= GameValues.GROUND))
            ySpeed -= 0.1;
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
