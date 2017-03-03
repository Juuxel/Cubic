package juuxel.cubic.creature.fx;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.Creature;

public abstract class Effect extends Creature
{
    public Effect(double x, double y)
    {
        setX(x);
        setY(y);

        setYSpeed(7);
        setXSpeed(getRandom().nextBoolean() ? -1 : 1);
    }

    @Override
    protected void logic()
    {
        setYSpeed(getYSpeed() - 0.1); // -= 0.1

        if (getY() < -50)
            Cubic.CREATURES.remove(this);
    }
}
