package juuxel.cubic.graphics;

import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class SpriteLoader
{
    private static final Map<String, Class<? extends Sprite>> SPRITES = new HashMap<>();

    public static Map<String, Class<? extends Sprite>> getSprites()
    {
        return Collections.unmodifiableMap(SPRITES);
    }

    public static void registerSprite(String functionId, Class<? extends Sprite> spriteClass)
    {
        SPRITES.put(functionId, spriteClass);
    }

    public static Sprite load(String file)
    {
        try
        {
            Properties props = new Properties();

            props.load(SpriteLoader.class.getResourceAsStream("/assets/sprites/" + file + ".sprite"));

            String function = props.getProperty("function");

            for (Map.Entry<String, Class<? extends Sprite>> entry : SPRITES.entrySet())
            {
                if (entry.getKey().equals(function))
                {
                    try
                    {
                        String texture = props.getProperty("textures");
                        System.out.println("[DEBUG] " + texture); // TODO Debug
                        return entry.getValue().getConstructor(String.class).newInstance(texture);
                    }
                    catch (Throwable e)
                    {
                        System.err.printf("Error in initializing sprite class '%s':%n", entry.getValue().getName());
                        e.printStackTrace();
                    }
                }
            }

            System.err.printf("Invalid function '%s' in sprite %s. %n", function, file);
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static void initialize()
    {
        registerSprite("default", SpriteDefault.class);
        registerSprite("random", SpriteRandom.class);
    }
}
