/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render.sprite;

import juuxel.cubic.lib.Images;

import java.awt.*;
import java.util.Properties;

/**
 * A simple sprite containing one image.
 */
public final class SpriteDefault extends Sprite
{
    private final Image image;

    public SpriteDefault(Properties props)
    {
        super(props);

        image = Images.load(getTexture() + ".png");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Image getImage(Object owner)
    {
        return image;
    }
}
