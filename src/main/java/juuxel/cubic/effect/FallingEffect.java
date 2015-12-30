package juuxel.cubic.effect;

import juuxel.cubic.Creature;
import juuxel.cubic.Cubic;
import juuxel.opengg.Graphics;

import java.awt.*;

public class FallingEffect extends Creature
{
    public final Color color;

    public FallingEffect(Color color, double x, double y)
    {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    @Override
    public void logic()
    {
        y -= 2;

        if (y < -50)
            Cubic.CREATURES.remove(this);
    }

    @Override
    public void draw(Graphics g)
    {
        Color oldColor = g.getGraphics2D().getColor();

        g.getGraphics2D().setColor(color);
        g.getGraphics2D().fillRect(calculateXInt(), calculateYInt(), 5, 5);

        g.getGraphics2D().setColor(oldColor);
    }
}
