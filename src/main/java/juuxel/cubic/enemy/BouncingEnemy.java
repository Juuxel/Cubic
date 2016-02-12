package juuxel.cubic.enemy;

import juuxel.cubic.reference.GameValues;
import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;

public class BouncingEnemy extends NormalEnemy
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
        drawCreature(g, Images.BOUNCING_ENEMY);
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
