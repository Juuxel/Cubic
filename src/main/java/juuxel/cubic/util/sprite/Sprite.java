package juuxel.cubic.util.sprite;

import java.awt.*;
import java.util.Properties;

public abstract class Sprite
{
    private final String texture;
    private final Properties props;

    public Sprite(Properties props)
    {
        this.props = props;
        this.texture = props.getProperty("textures");
    }

    public abstract Image getImage(Object o);

    public Image getImage()
    { return getImage(this); }

    public String getTexture()
    { return texture; }

    public Properties getProps()
    { return props; }
}
