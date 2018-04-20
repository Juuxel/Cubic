/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import juuxel.cubic.lib.Images;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

/**
 * A sprite loaded from a bigger sheet image.
 */
public final class SpriteMulti extends Sprite
{
    private Image image;

    public SpriteMulti(Properties props)
    {
        super(props);

        int x = Integer.valueOf(props.getProperty("xPos"));
        int y = Integer.valueOf(props.getProperty("yPos"));
        int width = 8, height = 8;

        if (props.containsKey("width"))
            width = Integer.valueOf(props.getProperty("width"));

        if (props.containsKey("height"))
            height = Integer.valueOf(props.getProperty("height"));

        if (props.containsKey("size"))
            width = height = Integer.valueOf(props.getProperty("size"));

        BufferedImage spriteSheet = Images.load(getTexture() + ".png");
        image = spriteSheet.getSubimage(x * width, y * height, width, height);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object owner)
    {
        return image;
    }
}
