package juuxel.cubic.graphics;

import juuxel.cubic.reference.Images;

import java.awt.*;
import java.util.Properties;

public class SpriteDefault extends Sprite
{
    private final Image image;

    public SpriteDefault(Properties props)
    {
        super(props);

        image = Images.load(getTexture() + ".png");
    }

    @Override
    public Image getImage()
    {
        return image;
    }
}
