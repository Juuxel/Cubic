/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * A sprite represents a texture object.
 */
public abstract class Sprite
{
    private final String texture;
    private final Map<String, String> props;
    private final boolean flippable;

    /**
     * The constructor. Use {@link SpriteLoader#load(String)}
     * for loading sprites from files.
     *
     * @param props the sprite properties
     * @see SpriteLoader#load(String) SpriteLoader.load(String)
     */
    public Sprite(Map<String, String> props)
    {
        this.props = props;
        this.texture = props.get("textures");
        this.flippable = Boolean.parseBoolean(props.getOrDefault("flippable", "false"));
    }

    /**
     * Gets the image of this sprite for an object.
     * The owner matters when having different images for different owners
     * (in {@link SpriteRandom}, for example).
     *
     * @param owner the owner
     * @return the sprite image for the owner
     */
    public abstract BufferedImage getImage(Object owner);

    /**
     * Gets the image of this sprite for this sprite object.
     * Useful when you don't need to care about the owner of the image.
     *
     * @return the sprite image for this object
     * @see #getImage(Object) getImage(Object)
     */
    public BufferedImage getImage()
    { return getImage(this); }

    /**
     * Gets the value of the {@code texture} variable.
     *
     * @return the value of {@code texture}
     */
    public String getTexture()
    { return texture; }

    /**
     * Gets the value of the {@code props} variable,
     * or the properties for this sprite.
     *
     * @return the value of {@code props}
     */
    public Map<String, String> getProps()
    { return props; }

    /**
     * Gets the value of {@code flippable}. If it's true, this sprite can flip.
     *
     * <p>{@code flippable} is the corresponding sprite file property.</p>
     *
     * @return the value of {@code flippable}
     * @see juuxel.cubic.creature.Creature#flippingEnabled
     */
    public boolean isFlippable()
    { return flippable; }
}
