package juuxel.cubic.render.sprite;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.Creature;
import juuxel.cubic.util.IBasicFunctions;
import juuxel.cubic.util.ICreatureListener;
import juuxel.cubic.util.Randomizer;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A sprite which chooses a random subsprite for every object.
 */
public class SpriteRandom extends Sprite implements ICreatureListener, IBasicFunctions
{
    private final Map<Object, Sprite> spriteMap = new HashMap<>();
    private final Sprite[] subsprites;

    public SpriteRandom(Properties props)
    {
        super(props);

        Cubic.CREATURE_LISTENERS.add(this);

        String[] spriteNames = commaSplit(getTexture());
        subsprites = new Sprite[spriteNames.length];

        for (int i = 0; i < spriteNames.length; i++)
        {
            subsprites[i] = SpriteLoader.load(spriteNames[i]);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object o)
    {
        return spriteMap.get(o).getImage(o);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatureCreated(Creature creature)
    {
        spriteMap.put(creature, Randomizer.getRandomObject(subsprites));
    }
}
