package juuxel.cubic.graphics;

import juuxel.cubic.reference.Images;

import java.awt.*;

public class SpriteDefault extends Sprite
{
    private final Image image;

    public SpriteDefault(String texture)
    {
        super(texture);

        image = Images.load(getTexture() + ".png");
    }

    @Override
    public Image getImage()
    {
        return image;
    }
}
