package juuxel.cubic.graphics;

import juuxel.cubic.reference.Images;

import java.awt.*;
import java.util.Properties;

public class SpriteDefault extends Sprite
{
    private final Image image;

    public SpriteDefault(Properties properties)
    {
        super(properties);

        image = Images.load(getTexture() + ".png");
    }

    @Override
    public Image getImage()
    {
        return image;
    }
}
