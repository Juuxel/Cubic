/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render;

import juuxel.cubic.render.sprite.Sprite;

import java.awt.*;

/**
 * This class is used for drawing on the screen.
 * A wrapper with extensions for AWT's Graphics/Graphics2D.
 * The underlying methods from {@code Graphics2D} can be used with {@link #getGraphics2D()}.
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

    /**
     * Draws an image on the canvas.
     *
     * @param image the image
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     */
    public void drawImage(Image image, int x, int y, int width, int height)
    {
        graphics2D.drawImage(image, x, y, x + width, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    /**
     * Draws a {@link Sprite sprite} on the canvas.
     *
     * @param sprite the sprite
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     *
     * @see #drawImage(Image, int, int, int, int)
     */
    public void drawImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawImage(sprite.getImage(), x, y, width, height);
    }

    /**
     * Draws an image on the canvas, flipped horizontally.
     *
     * @param image the image
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     *
     * @see #drawImage(Image, int, int, int, int)
     */
    public void drawFlippedImage(Image image, int x, int y, int width, int height)
    {
        graphics2D.drawImage(image, x + width, y, x, y + height, 0, 0, image.getWidth(null), image.getHeight(null), null);
    }

    /**
     * Draws a sprite on the canvas, flipped horizontally.
     *
     * @param sprite the sprite
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     *
     * @see #drawFlippedImage(Image, int, int, int, int)
     */
    public void drawFlippedImage(Sprite sprite, int x, int y, int width, int height)
    {
        drawFlippedImage(sprite.getImage(), x, y, width, height);
    }

    /**
     * Draws an image on the canvas with the opacity {@code alpha}.
     *
     * @param image the image
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     * @param alpha the opacity in the range {@code 0.0-1.0}
     *
     * @see #drawImage(Image, int, int, int, int)
     */
    public void drawImageWithAlpha(Image image, int x, int y, int width, int height, float alpha)
    {
        graphics2D.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        drawImage(image, x, y, width, height);
        graphics2D.setComposite(AlphaComposite.SrcOver);
    }

    /**
     * Draws an image on the canvas, possibly flipped horizontally.
     *
     * @param image the image
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     * @param flipped {@code true} if the image will be flipped horizontally
     */
    public void drawImage(Image image, int x, int y, int width, int height, boolean flipped)
    {
        if (flipped)
            drawFlippedImage(image, x, y, width, height);
        else
            drawImage(image, x, y, width, height);
    }

    /**
     * Draws a {@link Sprite sprite} on the canvas, possibly flipped horizontally.
     *
     * @param sprite the sprite
     * @param x the top-left x coordinate
     * @param y the top-left y coordinate
     * @param width the image width (image will be rescaled)
     * @param height the image height (image will be rescaled)
     * @param flipped {@code true} if the image will be flipped horizontally
     */
    public void drawImage(Sprite sprite, int x, int y, int width, int height, boolean flipped)
    {
        if (flipped)
            drawFlippedImage(sprite, x, y, width, height);
        else
            drawImage(sprite, x, y, width, height);
    }
}
