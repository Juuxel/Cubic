package juuxel.cubic.render.sprite;

import juuxel.cubic.lib.Images;

import java.awt.*;
import java.util.Properties;

/**
 * A simple sprite containing one image.
 */
public class SpriteDefault extends Sprite
{
    private final Image image;

    public SpriteDefault(Properties props)
    {
        super(props);

        image = Images.load(getTexture() + ".png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object o)
    {
        return image;
    }
}
