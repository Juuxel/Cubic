package juuxel.cubic.effect;

import juuxel.cubic.Cubic;
import juuxel.cubic.reference.Images;
import juuxel.opengg.Graphics;

public final class Life extends Effect
{
    public Life(double x, double y)
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
