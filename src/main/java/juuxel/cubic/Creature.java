package juuxel.cubic;

import juuxel.cubic.graphics.Graphics;

import java.awt.*;
import java.util.Random;

public abstract class Creature
{
    public double x, y, xSpeed, ySpeed;
    protected final Random random = new Random();

    public Creature()
    {
        Cubic.CREATURES.add(this);
    }

    public double calculateY()
    {
        return Cubic.calculateY(y);
    }

    public void kill()
    {
        x = Math.random() * Cubic.game.getWidth();
    }

    public void drawCreature(Graphics g, Image image)
    {
        int dx = calculateXInt(), dy = calculateYInt();

        g.getGraphics2D().drawImage(image, dx - 16, dy - 16, dx + 16, dy + 16, 0, 0, 8, 8, null);
    }

    public abstract void logic();
    public abstract void draw(Graphics g);

    public int calculateXInt()
    {
        return calculateInt(x);
    }

    public int calculateYInt()
    {
        return calculateInt(calculateY());
    }

    public int calculateInt(double d)
    {
        String string = String.valueOf(d);

        return Integer.valueOf(string.substring(0, string.indexOf('.')));
    }
}
