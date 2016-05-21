package juuxel.cubic.util.sprite;

import juuxel.cubic.util.IBasicFunctions;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Properties;

public class SpriteLayered extends Sprite implements IBasicFunctions
{
    private final BufferedImage image;

    public SpriteLayered(Properties props)
    {
        super(props);

        String[] subspriteNames = commaSplit(getTexture());
        Sprite[] subsprites = new Sprite[subspriteNames.length];
        int width = 0, height = 0;

        for (int i = 0; i < subspriteNames.length; i++)
        {
            Sprite sprite = subsprites[i] = SpriteLoader.load(subspriteNames[i]);
            width = Math.max(width, sprite.getImage().getWidth(null));
            height = Math.max(width, sprite.getImage().getHeight(null));
        }

        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (Sprite sprite : subsprites)
        {
            Graphics g = image.getGraphics();

            g.drawImage(sprite.getImage(), 0, 0, image.getWidth(), image.getHeight(), null);
        }
    }

    @Override
    public Image getImage(Object o)
    {
        return image;
    }
}
