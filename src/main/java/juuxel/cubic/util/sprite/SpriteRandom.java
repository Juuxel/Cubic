package juuxel.cubic.util.sprite;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.Creature;
import juuxel.cubic.util.ICreatureListener;
import juuxel.cubic.util.Randomizer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SpriteRandom extends Sprite implements ICreatureListener
{
    private final Map<Object, Sprite> spriteMap = new HashMap<>();
    private final Sprite[] subsprites;

    public SpriteRandom(Properties props)
    {
        super(props);

        Cubic.CREATURE_LISTENERS.add(this);

        String[] spriteNames = getTexture().split(", *");
        subsprites = new Sprite[spriteNames.length];

        for (int i = 0; i < spriteNames.length; i++)
        {
            subsprites[i] = SpriteLoader.load(spriteNames[i]);
        }
    }

    @Override
    public Image getImage(Object o)
    {
        return spriteMap.get(o).getImage(o);
    }

    @Override
    public void onCreatureCreated(Creature creature)
    {
        spriteMap.put(creature, Randomizer.getRandomObject(subsprites));
    }
}
