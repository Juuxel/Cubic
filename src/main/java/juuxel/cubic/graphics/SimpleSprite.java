package juuxel.cubic.graphics;

import juuxel.cubic.reference.Images;

import java.awt.*;

public class SimpleSprite extends Sprite
{
    private final Image image;

    public SimpleSprite(String texture)
    {
        image = Images.load(texture + ".png");
    }

    @Override
    public Image getImage()
    {
        return image;
    }
}
