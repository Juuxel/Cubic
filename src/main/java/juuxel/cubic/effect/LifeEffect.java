package juuxel.cubic.effect;

import juuxel.cubic.Cubic;
import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;

public final class LifeEffect extends Effect
{
    public LifeEffect(double x, double y)
    {
        super(x, y);

        Cubic.lives++;
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.LIFE);
    }
}