package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.IBasicFunctions;
import juuxel.cubic.util.render.Graphics;
import juuxel.cubic.util.sprite.Sprite;

import java.awt.*;
import java.util.Random;

public abstract class Creature implements IBasicFunctions
{
    public final int id;
    protected double x, y;
    public double xSpeed = 0, ySpeed = 0;
    protected final Random random = new Random();
    public int spriteWidth = 32, spriteHeight = 32;

    private Sprite sprite;
    private boolean collisionEnabled, flippingEnabled;
    private Direction direction = Direction.RIGHT;
    protected int speedModifierX = 1, speedModifierY = 1;

    public Creature()
    {
        id = Cubic.CREATURES.size();
        Cubic.CREATURES.add(this);
        Cubic.CREATURE_LISTENERS.forEach(listener -> listener.onCreatureCreated(this));
        setCollisionEnabled(false);
        setFlippingEnabled(false);
    }

    public double calculateY()
    { return calculateY(y); }

    public void kill()
    {}

    private void drawCreature(Graphics g, Image image, int width, int height)
    {
        int dx = (int) x, dy = (int) calculateY();

        if (direction == Direction.RIGHT)
            g.drawImage(image, dx - width / 2, dy - height / 2, width, height);
        else
            g.drawFlippedImage(image, dx - width / 2, dy - height / 2, width, height);
    }

    protected void drawCreature(Graphics g, Sprite sprite)
    { drawCreature(g, sprite.getImage(this), spriteWidth, spriteHeight); }

    public final void executeLogic()
    {
        moveX(xSpeed / speedModifierX);

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

    public void draw(Graphics g)
    {
        if (getSprite() != null)
            drawCreature(g, getSprite());
    }

    public Sprite getSprite()
    { return sprite; }

    public void setSprite(Sprite sprite)
    { this.sprite = sprite; }

    public boolean onGround()
    { return y <= GameValues.GROUND; }

    public boolean isCollisionEnabled()
    { return collisionEnabled; }

    public void setCollisionEnabled(boolean collisionEnabled)
    { this.collisionEnabled = collisionEnabled; }

    public boolean isFlippingEnabled()
    { return flippingEnabled; }

    public void setFlippingEnabled(boolean b)
    { flippingEnabled = b; }

    public void moveX(double xOffset)
    {
        if (isFlippingEnabled())
        {
            Direction d = getDirection();

            if (xOffset > 0)
                d = Direction.RIGHT;
            else if (xOffset < 0)
                d = Direction.LEFT;

            setDirection(d);
        }

        x += xOffset;
    }

    public void moveY(double yOffset)
    { y += yOffset; }

    public double getX()
    { return x; }

    public double getY()
    { return y; }

    public Direction getDirection()
    { return direction; }

    public void setDirection(Direction d2)
    { direction = d2; }
}
