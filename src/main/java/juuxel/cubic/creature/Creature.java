package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.util.render.Graphics;
import juuxel.cubic.util.sprite.Sprite;

import java.awt.*;
import java.util.Random;

public abstract class Creature
{
    public final int id;
    public double x, y, xSpeed = 0, ySpeed = 0;
    protected final Random random = new Random();
    public int spriteWidth = 32, spriteHeight = 32;

    private Sprite sprite;
    private boolean collisionEnabled;
    protected int speedModifierX = 1, speedModifierY = 1;

    public Creature()
    {
        id = Cubic.CREATURES.size();
        Cubic.CREATURES.add(this);
        Cubic.CREATURE_LISTENERS.forEach(listener -> listener.onCreatureCreated(this));
        setCollisionEnabled(false);
    }

    public double calculateY()
    {
        return Cubic.calculateY(y);
    }

    public void kill()
    {
        x = Math.random() * Cubic.game.getWidth();
    }

    private void drawCreature(Graphics g, Image image, int width, int height)
    {
        int dx = calculateXInt(), dy = calculateYInt();

        g.drawImage(image, dx - width / 2, dy - height / 2, width, height);
    }

    protected void drawCreature(Graphics g, Sprite sprite)
    {
        drawCreature(g, sprite.getImage(this), spriteWidth, spriteHeight);
    }

    public final void executeLogic()
    {
        x += xSpeed / speedModifierX;

        if (isCollisionEnabled())
        {
            double yNew = y + ySpeed / speedModifierY;

            if (yNew < GameValues.GROUND)
                yNew = GameValues.GROUND;

            y = yNew;

            if (onGround())
                ySpeed = 0;
        }
        else
            y += ySpeed / speedModifierY;

        logic();
    }

    protected abstract void logic();

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

    public void draw(Graphics g)
    {
        if (getSprite() != null)
            drawCreature(g, getSprite());
    }

    public Sprite getSprite()
    {
        return sprite;
    }

    public void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
    }

    public boolean onGround()
    {
        return y <= GameValues.GROUND;
    }

    public boolean isCollisionEnabled()
    {
        return collisionEnabled;
    }

    public void setCollisionEnabled(boolean collisionEnabled)
    {
        this.collisionEnabled = collisionEnabled;
    }
}
