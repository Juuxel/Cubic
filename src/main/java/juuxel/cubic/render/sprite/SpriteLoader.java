/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import java.io.IOException;
import java.util.Collections;
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
     * Returns an unmodifiable copy of the sprite provider map.
     *
     * @return a copy of the sprite provider map
     */
    public static Map<String, Function<Properties, Sprite>> getSpriteProviders()
    {
        return Collections.unmodifiableMap(SPRITE_PROVIDERS);
    }

    /**
     * Registers a sprite provider for a function name.
     * Modded functions should contains the mod name for
     * mod compatibility.
     *
     * @param functionId the function name
     * @param provider the sprite provider
     */
    public static void registerSprite(String functionId, Function<Properties, Sprite> provider)
    {
        SPRITE_PROVIDERS.put(functionId, provider);
    }

    /**
     * Gets a Sprite from the classpath using the sprite file's <code>function</code> property.
     * The location this method looks for is <code>"/data/sprites/" + sprite + ".sprite"</code>
     * If no function matching the property is found, returns null.
     *
     * If the sprite file is not found, tries to construct a Sprite from the {@code sprite} parameter.
     * This method looks for an image file in the {@code /data/images} directory. If one is found, returns
     * a new Sprite.
     *
     * @param sprite the sprite name
     * @return the sprite, or null if function not found
     */
    public static Sprite load(String sprite)
    {
        try
        {
            Properties props = new Properties();

            props.load(SpriteLoader.class.getResourceAsStream("/data/sprites/" + sprite + ".sprite"));

            String function = props.getProperty("function");

            if (function == null)
                function = "default";

            for (Map.Entry<String, Function<Properties, Sprite>> entry : SPRITE_PROVIDERS.entrySet())
            {
                if (entry.getKey().equals(function))
                {
                    return entry.getValue().apply(props);
                }
            }

            System.err.printf("Invalid function '%s' in sprite %s. %n", function, sprite);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        catch (Exception e)
        {
            // Try to construct the missing sprite from the texture

            Properties p = new Properties();
            p.setProperty("function", "default");
            p.setProperty("textures", sprite);

            Sprite s = new SpriteDefault(p);

            if (s.getImage() == null)
                e.printStackTrace();
            else
                return s;
        }

        return null;
    }

    /**
     * An internal method to register Cubic's default sprites.
     * Mods, please don't call this.
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
