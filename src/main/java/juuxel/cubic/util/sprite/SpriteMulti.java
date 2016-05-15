package juuxel.cubic.util.sprite;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;

import java.awt.*;
import java.awt.image.CropImageFilter;
import java.awt.image.FilteredImageSource;
import java.util.Properties;

public class SpriteMulti extends Sprite implements ISpriteHandler
{
    private Image image;

    public SpriteMulti(Properties props)
    {
        super(props);

        Cubic.SPRITE_HANDLERS.add(this);
    }

    @Override
    public Image getImage(Object o)
    {
        return image;
    }

    @Override
    public void onSpriteCreate()
    {
        Properties props = getProps();

        int x = Integer.valueOf(props.getProperty("xPos"));
        int y = Integer.valueOf(props.getProperty("yPos"));
        int width = 8, height = 8;

        if (props.containsKey("width"))
            width = Integer.valueOf(props.getProperty("width"));

        if (props.containsKey("height"))
            height = Integer.valueOf(props.getProperty("height"));

        if (props.containsKey("size"))
            width = height = Integer.valueOf(props.getProperty("size"));

        Image spriteSheet = Images.load(getTexture() + ".png");

        image = Cubic.game.getGameFrame().createImage(new FilteredImageSource(spriteSheet.getSource(), new CropImageFilter(x * width, y * height, width, height)));
    }
}
