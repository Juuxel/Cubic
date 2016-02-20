package juuxel.cubic;

import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.graphics.Sprite;

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

    protected void drawCreature(Graphics g, Image image)
    {
        drawCreature(g, image, 32, 32);
    }

    protected void drawCreature(Graphics g, Image image, int width, int height)
    {
        int dx = calculateXInt(), dy = calculateYInt();

        g.drawImage(image, dx - width / 2, dy - height / 2, width, height);
    }

    protected void drawCreature(Graphics g, Sprite sprite)
    {
        drawCreature(g, sprite.getImage());
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
