package juuxel.cubic.effect;

import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;

public final class Death extends Effect
{
    public Death(double x, double y)
    {
        super(x, y);
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.DEATH);
    }
}
