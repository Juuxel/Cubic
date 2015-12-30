package juuxel.cubic.enemy;

import juuxel.cubic.reference.GameValues;
import juuxel.cubic.reference.Images;
import juuxel.opengg.Graphics;

public class BouncingEnemy extends Enemy
{
    public BouncingEnemy()
    {
        super(Images.BOUNCING_ENEMY);
    }

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

        ySpeed -= 0.1;

        if (y <= GameValues.GROUND)
        {
            y = GameValues.GROUND;
            ySpeed = 0;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.BOUNCING_ENEMY);
    }
}
