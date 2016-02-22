package juuxel.cubic.graphics;

import juuxel.cubic.Cubic;
import juuxel.cubic.reference.Images;
import juuxel.cubic.util.Randomizer;

import java.awt.*;
import java.util.Properties;

public class SpriteRandom extends Sprite implements ISpriteHandler
{
    private Image image;
    private final Image[] images;

    public SpriteRandom(Properties properties)
    {
        super(properties);

        Cubic.SPRITE_HANDLERS.add(this);

        try
        {
            String[] textureNames = getTexture().split(", ");
            images = new Image[textureNames.length];

            for (int i = 0; i < textureNames.length; i++)
            {
                images[i] = Images.load(textureNames[i] + ".png");
            }
        }
        catch (Exception e)
        {
            throw new RuntimeException("Error in texture initialization for function 'random'.", e);
        }
    }

    @Override
    public void onSpriteBake()
    {
        image = Randomizer.getRandomObject(images);
    }

    @Override
    public Image getImage()
    {
        return image;
    }
}
