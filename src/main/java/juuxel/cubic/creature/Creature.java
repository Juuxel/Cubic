/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.event.CreatureCreationEvent;
import juuxel.cubic.event.EventBus;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.Utils;

import java.awt.*;
import java.util.Random;

public abstract class Creature
{
    protected double x, y;
    public double xSpeed = 0, ySpeed = 0;
    protected final Random random = new Random();
    public int spriteWidth = 32, spriteHeight = 32;

    private Sprite sprite;
    public boolean collidesWithGround, flippingEnabled;
    public Direction direction = Direction.RIGHT;
    protected int speedModifierX = 1, speedModifierY = 1;

    public Creature()
    {
        Cubic.CREATURES.add(this);
        EventBus.post(new CreatureCreationEvent(this));
        collidesWithGround = false;
        flippingEnabled = false;
    }

    public double yOnScreen()
    { return Utils.yOnScreen(y); }

    public void kill()
    {}

    private void drawCreature(Graphics g, Image image, int width, int height)
    {
        int dx = (int) x, dy = (int) yOnScreen();

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

        if (collidesWithGround)
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

    protected void setSprite(Sprite sprite)
    {
        this.sprite = sprite;
        this.flippingEnabled = sprite.isFlippable();
    }

    public boolean onGround()
    { return y <= GameValues.GROUND; }

    public void updateSpriteDirection(double value)
    {
        if (flippingEnabled)
        {
            if (value > 0)
                direction = Direction.RIGHT;
            else if (value < 0)
                direction = Direction.LEFT;
        }
    }

    public void updateSpriteDirection()
    {
        updateSpriteDirection(xSpeed);
    }

    public void moveX(double xOffset)
    {
        updateSpriteDirection(xOffset);

        x += xOffset;
    }

    public void moveY(double yOffset)
    { y += yOffset; }

    public double getX()
    { return x; }

    public double getY()
    { return y; }
}
