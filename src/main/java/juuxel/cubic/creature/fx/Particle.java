package juuxel.cubic.creature.fx;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.Cubic;
import juuxel.cubic.render.Graphics;

import java.awt.*;

public class Particle extends Creature
{
    public final Color color;
    public int lifetime = 0;

    public Particle(Color color, double x, double y)
    {
        this.color = color;
        this.x = x;
        this.y = y;
    }

    @Override
    public void logic()
    {
        lifetime++;

        if (lifetime >= 100)
            Cubic.CREATURES.remove(this);
    }

    @Override
    public void draw(Graphics g)
    {
        Color oldColor = g.getGraphics2D().getColor();

        g.getGraphics2D().setColor(color);
        g.getGraphics2D().fillRect((int) x, (int) yOnScreen(), 5, 5);

        g.getGraphics2D().setColor(oldColor);
    }
}
