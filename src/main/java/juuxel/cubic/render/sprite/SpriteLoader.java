/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.function.Function;

/**
 * SpriteLoader loads sprites from the classpath.
 */
public final class SpriteLoader
{
    private static final Map<String, Function<Properties, Sprite>> SPRITE_PROVIDERS = new HashMap<>();

    /**
     * Registers a sprite provider for a type name.
     * Modded functions should contains the mod name for
     * mod compatibility.
     *
     * @param type the type name
     * @param provider the sprite provider
     */
    public static void registerSprite(String type, Function<Properties, Sprite> provider)
    {
        SPRITE_PROVIDERS.put(type, provider);
    }

    /**
     * Gets a Sprite from the classpath using the sprite file's <code>function</code> property.
     * The location this method looks for is <code>"/data/sprites/" + sprite + ".sprite"</code>
     * If no type matching the property is found, throws an exception.
     *
     * If the sprite file is not found, tries to construct a Sprite from the {@code sprite} parameter.
     * This method looks for an image file in the {@code /data/images} directory. If one is found, returns
     * a new Sprite.
     *
     * @param sprite the sprite name
     * @return the sprite
     */
    public static Sprite load(String sprite)
    {
        try
        {
            Properties props = new Properties();
            String path = "/data/sprites/" + sprite + ".sprite";

            if (SpriteLoader.class.getResource(path) == null)
            {
                // Try to construct the missing sprite from the texture

                Properties p = new Properties();
                p.setProperty("type", "default");
                p.setProperty("textures", sprite);

                return new SpriteDefault(p);
            }

            props.load(SpriteLoader.class.getResourceAsStream(path));

            String type = props.getProperty("type");

            if (type == null)
                type = "default";

            for (Map.Entry<String, Function<Properties, Sprite>> entry : SPRITE_PROVIDERS.entrySet())
            {
                if (entry.getKey().equals(type))
                {
                    return entry.getValue().apply(props);
                }
            }

            throw new IllegalArgumentException(String.format("Invalid type '%s' in sprite %s. %n", type, sprite));
        }
        catch (IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    /**
     * An internal method to register Cubic's default sprites.
     * Can be called multiple times, but doesn't do anything after the first call.
     */
    public static void registerDefaults()
    {
        registerSprite("default", SpriteDefault::new);
        registerSprite("random", SpriteRandom::new);
        registerSprite("multi", SpriteMulti::new);
        registerSprite("layered", SpriteLayered::new);
        registerSprite("animated", SpriteAnimated::new);
    }
}
