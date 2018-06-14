/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

import java.awt.*;

public final class RenderEngine
{
    private static final Color SKY_TOP = new Color(0x80caeb);
    private static final Color SKY_BOTTOM = new Color(0x7cdeeb);

    private RenderEngine()
    {}

    public static void repaint(Graphics g)
    {
        if (!Cubic.inStartScreen)
        {
            drawSky(g);
            drawScore(g);
            drawLevel(g);

            Cubic.CREATURES.forEach(creature -> creature.draw(g));

            if (Cubic.player.lives <= 0)
            {
                int dx = Cubic.getWidth() / 2, dy = Cubic.getHeight() / 2;
                Graphics2D g2D = g.getGraphics2D();
                g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                g2D.setFont(GameValues.FONT.deriveFont(36F));
                g2D.drawString(Translator.translate("game.gameOver"), dx - 120, dy - 36);

                Cubic.running = false;
            }
        }
    }

    private static void drawScore(Graphics g)
    {
        g.drawImage(Images.score, 5, 10, 16, 16);
        g.drawImage(Images.level, 5, 30, 16, 16);
        g.drawImage(Images.levelUpIcon, 5, 50, 16, 16);
        g.drawImage(Images.heart, 5, 70, 16, 16);

        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.player.score), 30, 10, 1F);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.player.level), 30, 30, 1F);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d / %,d", Cubic.ENEMIES.size(), Cubic.player.level), 30, 50, 1F);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.player.lives), 30, 70, 1F);
    }

    public static void drawLevel(Graphics g)
    {
        Cubic.world.draw(g);
    }

    public static void drawSky(Graphics g)
    {
        Paint paint = g.getGraphics2D().getPaint();

        g.getGraphics2D().setPaint(new GradientPaint(0, 0, SKY_TOP, 0, Cubic.getHeight(), SKY_BOTTOM));
        g.getGraphics2D().fillRect(0, 0, Cubic.getWidth(), Cubic.getHeight());

        g.getGraphics2D().setPaint(paint);
    }

    public static void drawNumberString(Graphics g, String s, int x, int y, float alpha)
    {
        char[] chars = s.toCharArray();

        g.getGraphics2D().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));

        for (int i = 0; i < chars.length; i++)
        {
            g.drawImage(Images.numbers.get(chars[i]), x + i * 10, y, 8, 16);
        }

        g.getGraphics2D().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
    }
}
