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
public class SpriteLoader
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
     * Gets a Sprite from the classpath using
     * the sprite file's <code>function</code> property.
     * The location this method looks for is <code>"/data/sprites/" + sprite + ".sprite"</code>
     * If no function matching the property is found, returns null.
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
    }
}
