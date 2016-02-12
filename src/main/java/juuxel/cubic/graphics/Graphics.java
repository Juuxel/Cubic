package juuxel.cubic.graphics;

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

    public void drawString(String str, int x, int y)
    {
        graphics2D.drawString(str, x, y);
    }

    public void drawImage(Image image, int x, int y, int width, int height)
    {
        graphics2D.drawImage(image, x, y, width, height, null);
    }

    public void drawImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawImage(sprite.getImage(), x, y, width, height);
    }
}
