/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import juuxel.cubic.event.CreatureCreationEvent;
import juuxel.cubic.event.EventBus;
import juuxel.cubic.event.EventHandler;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.util.Utils;

import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * A sprite which chooses a random subsprite for every object.
 */
public final class SpriteRandom extends Sprite implements EventHandler<CreatureCreationEvent>
{
    private final Map<Object, Sprite> spriteMap = new HashMap<>();
    private final Sprite[] subsprites;

    public SpriteRandom(Map<String, String> props)
    {
        super(props);

        EventBus.subscribe(CreatureCreationEvent.class, this);

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
    public BufferedImage getImage(Object owner)
    {
        if (!spriteMap.containsKey(owner))
            addToSpriteMap(owner);

        return spriteMap.get(owner).getImage(owner);
    }

    /**
     * {@inheritDoc}
     *
     * <p>Adds the event's creature to the internal sprite map.</p>
     */
    @Override
    public void handle(CreatureCreationEvent event)
    {
        addToSpriteMap(event.getCreature());
    }

    private void addToSpriteMap(Object o)
    {
        spriteMap.put(o, Randomizer.getRandomObject(subsprites));
    }
}
