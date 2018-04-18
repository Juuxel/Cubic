package juuxel.cubic.render.sprite;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.Effect;
import juuxel.cubic.util.Utils;

import java.awt.Image;
import java.util.*;

public final class SpriteAnimated extends Sprite
{
    private final Sprite[] sprites;
    private final double slowness;
    private double framesPerAgePoint = -1;

    public SpriteAnimated(Properties props)
    {
        super(props);

        if (props.containsKey("autoFind"))
        {
            int amount = Integer.parseInt(props.getProperty("autoFind"));

            sprites = new Sprite[amount];

            for (int i = 0; i < amount; i++)
            {
                sprites[i] = SpriteLoader.load(props.getProperty("textures") + i);
            }
        }
        else
        {
            String[] spriteNames = Utils.commaSplit(getTexture());
            sprites = new Sprite[spriteNames.length];

            for (int i = 0; i < spriteNames.length; i++)
            {
                sprites[i] = SpriteLoader.load(spriteNames[i]);
            }
        }

        if (props.containsKey("speed"))
            slowness = 1 / Double.parseDouble(props.getProperty("speed"));
        else
            slowness = 1;
    }

    @Override
    public Image getImage(Object owner)
    {
        if (owner instanceof Effect)
        {
            if (framesPerAgePoint == -1)
            {
                framesPerAgePoint = (double) sprites.length / (double) Effect.MAX_AGE;
            }

            return sprites[sprites.length - 1 - ((int) (((Effect) owner).getAge() * framesPerAgePoint) % sprites.length)].getImage(owner);
        }

        return sprites[(int) (Cubic.getTick() / slowness) % sprites.length].getImage(owner);
    }
}
