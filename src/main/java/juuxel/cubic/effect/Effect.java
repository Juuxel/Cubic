package juuxel.cubic.effect;

import juuxel.cubic.Creature;
import juuxel.cubic.Cubic;

public abstract class Effect extends Creature
{
    public Effect(double x, double y)
    {
        this.x = x;
        this.y = y;

        ySpeed = 7;
        xSpeed = random.nextBoolean() ? -1 : 1;
    }

    @Override
    public void logic()
    {
        x += xSpeed;
        y += ySpeed;
        ySpeed -= 0.1;


        if (y < -50)
            Cubic.CREATURES.remove(this);
    }
}
