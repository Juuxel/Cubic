package juuxel.cubic.graphics;

import java.io.IOException;
import java.util.Properties;

public class SpriteLoader
{
    public static Sprite load(String file)
    {
        try
        {
            Properties props = new Properties();

            props.load(SpriteLoader.class.getResourceAsStream("/assets/sprites/" + file + ".sprite"));

            Sprite sprite = null;

            switch (props.getProperty("function"))
            {
                case "random":
                    sprite = new RandomSprite(props.getProperty("textures"));
                    break;
                case "default":
                    sprite = new SimpleSprite(props.getProperty("textures"));
                    break;
                default:
                    System.err.printf("Invalid function '%s' in %s.%n", props.getProperty("function"), file);
                    break;
            }

            return sprite;
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
