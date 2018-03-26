package juuxel.cubic.render.sprite;

import juuxel.cubic.util.Utils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A sprite consisting of multiple sprite layers.
 */
public class SpriteLayered extends Sprite
{
    private final Map<Object, Image> imageMap;

    public SpriteLayered(Properties props)
    {
        super(props);

        imageMap = new HashMap<>();
    }

    private Image addToMap(Object o)
    {
        String[] subspriteNames = Utils.commaSplit(getTexture());
        Sprite[] subsprites = new Sprite[subspriteNames.length];
        int width = 0, height = 0;

        for (int i = 0; i < subspriteNames.length; i++)
        {
            Sprite sprite = subsprites[i] = SpriteLoader.load(subspriteNames[i]);
            width = Math.max(width, sprite.getImage().getWidth(null));
            height = Math.max(width, sprite.getImage().getHeight(null));
        }

        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (Sprite sprite : subsprites)
        {
            Graphics g = image.getGraphics();

            g.drawImage(sprite.getImage(), 0, 0, image.getWidth(), image.getHeight(), null);
        }

        imageMap.put(o, image);

        return image;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object owner)
    {
        return imageMap.containsKey(owner) ? imageMap.get(owner) : addToMap(owner);
    }
}
