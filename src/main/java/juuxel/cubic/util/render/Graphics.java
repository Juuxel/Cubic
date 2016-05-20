package juuxel.cubic.util.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.util.sprite.Sprite;

import java.awt.*;

public class Graphics
{
    private final Graphics2D graphics2D;

    private Graphics(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }

    public Graphics2D getGraphics2D()
    {
        return graphics2D;
    }

    public static Graphics fromGraphics2D(Graphics2D graphics2D)
    {
        return new Graphics(graphics2D);
    }

    public static Graphics fromAWTGraphics(java.awt.Graphics graphics)
    {
        return fromGraphics2D((Graphics2D) graphics);
    }

    public void drawString(String str, int x, int y)
    {
        graphics2D.drawString(str, x, y);
    }

    public void drawImage(Image image, int x, int y, int width, int height)
    {
//        graphics2D.drawImage(image, x, y, width, height, null);
        graphics2D.drawImage(image, x, y, x + width, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    public void drawImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawImage(sprite.getImage(Cubic.game), x, y, width, height);
    }

    public void drawFlippedImage(Image image, int x, int y, int width, int height)
    {
        graphics2D.drawImage(image, x + width, y, x, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    public void drawFlippedImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawFlippedImage(sprite.getImage(Cubic.game), x, y, width, height);
    }
}
