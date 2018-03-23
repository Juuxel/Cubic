package juuxel.cubic.lib;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;

public final class GameValues
{
    public static final double GROUND = 80;

    public static final Font FONT;

    static
    {
        Font f = Font.decode("Arial");

        try
        {
            f = Font.createFont(Font.TRUETYPE_FONT, GameValues.class.getResourceAsStream("/data/fonts/Connection.otf"))
                    .deriveFont(16F);
        }
        catch (FontFormatException | IOException e)
        {
            e.printStackTrace();
        }

        FONT = f;
    }
}
