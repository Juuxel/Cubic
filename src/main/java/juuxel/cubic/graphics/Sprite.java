package juuxel.cubic.graphics;

import java.awt.*;
import java.util.Properties;

/* Sprites always should have a constructor of MySprite(java.util.Properties) */
public abstract class Sprite
{
    private final String texture;
    private final Properties properties;

    public Sprite(Properties properties)
    {
        this.properties = properties;
        this.texture = properties.getProperty("textures");
    }

    public abstract Image getImage();

    public String getTexture()
    {
        return texture;
    }

    public Properties getProperties()
    {
        return properties;
    }
}
