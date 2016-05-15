package juuxel.cubic.util.sprite;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SpriteLoader
{
    private static final Map<String, SpriteCreator> SPRITE_CREATORS = new HashMap<>();

    public static Map<String, SpriteCreator> getSpriteCreators()
    {
        return Collections.unmodifiableMap(SPRITE_CREATORS);
    }

    public static void registerSprite(String functionId, SpriteCreator creator)
    {
        SPRITE_CREATORS.put(functionId, creator);
    }

    public static Sprite load(String sprite)
    {
        try
        {
            Properties props = new Properties();

            props.load(SpriteLoader.class.getResourceAsStream("/data/sprites/" + sprite + ".sprite"));

            String function = props.getProperty("function");

            for (Map.Entry<String, SpriteCreator> entry : SPRITE_CREATORS.entrySet())
            {
                if (entry.getKey().equals(function))
                {
                    return entry.getValue().create(props);
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

    public static void initialize()
    {
        registerSprite("default", SpriteDefault::new);
        registerSprite("random", SpriteRandom::new);
        registerSprite("multi", SpriteMulti::new);
    }

    @FunctionalInterface
    public interface SpriteCreator
    {
        Sprite create(Properties props);
    }
}
