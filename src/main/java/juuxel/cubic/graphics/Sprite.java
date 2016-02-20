package juuxel.cubic.graphics;

import java.awt.*;

/* Sprites always should have a constructor of MySprite(String) */
public abstract class Sprite
{
    private final String texture;

    public Sprite(String texture)
    {
        this.texture = texture;
    }

    public abstract Image getImage();

    public String getTexture()
    {
        return texture;
    }
}
