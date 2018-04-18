package juuxel.cubic.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.render.sprite.Sprite;

import java.awt.*;

/**
 * This class is used for drawing on the screen.
 * A wrapper for AWT's Graphics/Graphics2D.
 */
public final class Graphics
{
    private final Graphics2D graphics2D;

    private Graphics(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }

    /**
     * Returns the underlying Graphics2D object.
     *
     * @return the Graphics2D object
     */
    public Graphics2D getGraphics2D()
    {
        return graphics2D;
    }

    /**
     * Creates a new Graphics object from AWT's Graphics2D.
     *
     * @param graphics2D the Graphics2D object
     * @return a new Graphics object
     */
    public static Graphics fromGraphics2D(Graphics2D graphics2D)
    {
        return new Graphics(graphics2D);
    }

    /**
     * Creates a new Graphics object from AWT's Graphics.
     *
     * @param graphics the AWT Graphics object
     * @return a new Graphics object
     * @throws ClassCastException when <code>graphics</code> is not an instance of Graphics2D
     */
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
        graphics2D.drawImage(image, x, y, x + width, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    public void drawImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawImage(sprite.getImage(), x, y, width, height);
    }

    public void drawFlippedImage(Image image, int x, int y, int width, int height)
    {
        graphics2D.drawImage(image, x + width, y, x, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    public void drawFlippedImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawFlippedImage(sprite.getImage(), x, y, width, height);
    }

    public void drawImageWithAlpha(Image image, int x, int y, int width, int height, float alpha)
    {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        drawImage(image, x, y, width, height);
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }
}
