package juuxel.cubic.graphics;

import java.awt.*;

public class Graphics
{
    private final Graphics2D graphics2D;

    private Graphics(Graphics2D graphics2D)
    {
        this.graphics2D = graphics2D;
    }

    public Graphics2D getGraphics2D()
    {
        return graphics2D;
    }

    public static Graphics fromGraphics2D(Graphics2D graphics2D)
    {
        return new Graphics(graphics2D);
    }
}
