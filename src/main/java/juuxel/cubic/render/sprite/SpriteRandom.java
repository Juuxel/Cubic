/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.Creature;
import juuxel.cubic.util.CreatureListener;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.util.Utils;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A sprite which chooses a random subsprite for every object.
 */
public final class SpriteRandom extends Sprite implements CreatureListener
{
    private final Map<Object, Sprite> spriteMap = new HashMap<>();
    private final Sprite[] subsprites;

    public SpriteRandom(Properties props)
    {
        super(props);

        Cubic.CREATURE_LISTENERS.add(this);

        String[] spriteNames = Utils.commaSplit(getTexture());
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
    public Image getImage(Object owner)
    {
        if (!spriteMap.containsKey(owner))
            addToSpriteMap(owner);

        return spriteMap.get(owner).getImage(owner);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void onCreatureCreated(Creature creature)
    {
        addToSpriteMap(creature);
    }

    private void addToSpriteMap(Object o)
    {
        spriteMap.put(o, Randomizer.getRandomObject(subsprites));
    }
}
