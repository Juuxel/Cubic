package juuxel.cubic.render.sprite;

import java.awt.*;
import java.util.Properties;

/**
 * A sprite represents a texture object.
 */
public abstract class Sprite
{
    private final String texture;
    private final Properties props;

    /**
     * The constructor. Please use {@link SpriteLoader#load(String)}
     * for loading sprites from files.
     *
     * @param props the sprite properties
     * @see SpriteLoader#load(String) SpriteLoader.load(String)
     */
    public Sprite(Properties props)
    {
        this.props = props;
        this.texture = props.getProperty("textures");
    }

    /**
     * Gets the image of this sprite for an object.
     *
     * @param o the object
     * @return the sprite image for the object <code>o</code>
     */
    public abstract Image getImage(Object o);

    /**
     * Gets the image of this sprite for this sprite object.
     * Useful when you don't need to care about the object for the image.
     *
     * @return the sprite image for this object
     * @see #getImage(Object) getImage(Object)
     */
    public Image getImage()
    { return getImage(this); }

    /**
     * Gets the value of the <code>texture</code> variable.
     *
     * @return the value of <code>texture</code>
     */
    public String getTexture()
    { return texture; }

    /**
     * Gets the value of the <code>props</code> variable,
     * or the properties for this sprite.
     *
     * @return the value of <code>props</code>
     */
    public Properties getProps()
    { return props; }
}
