package juuxel.cubic.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.options.KeyBinding;
import juuxel.cubic.options.Options;
import juuxel.cubic.util.Translator;

import java.awt.*;
import java.util.*;
import java.util.List;

import static juuxel.cubic.Cubic.START_SCREEN;
import static juuxel.cubic.Cubic.OPTIONS;
import static juuxel.cubic.Cubic.LANGUAGE_SCREEN;
import static juuxel.cubic.Cubic.CONTROLS;

public final class RenderEngine
{
    public static final Color SKY = new Color(0x80daeb);
    public static final Color SKY2 = new Color(0x7ceeeb);

    private RenderEngine()
    {}

    public static RenderEngine INSTANCE = new RenderEngine();

    public static void repaint()
    {
        // TODO Implement FPS limit
        Cubic.game.repaint();
    }

    public void repaint(Graphics g)
    {
        if (!Cubic.inStartScreen)
        {
            drawSky(g);
            drawScore(g);
            drawLevel(g);

            Cubic.CREATURES.forEach(creature -> creature.draw(g));

            if (Cubic.lives <= 0)
            {
                int dx = Cubic.game.getWidth() / 2, dy = Cubic.game.getHeight() / 2;
                Graphics2D g2D = g.getGraphics2D();
                g2D.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
                g2D.setFont(GameValues.FONT.deriveFont(36F));
                g2D.drawString(Translator.translate("game.gameOver"), dx - 120, dy - 36);

                Cubic.running = false;
            }
        }
    }

    private void drawScore(Graphics g)
    {
        g.drawImage(Images.score, 5, 10, 16, 16);
        g.drawImage(Images.level, 5, 30, 16, 16);
        g.drawImage(Images.levelUpIcon, 5, 50, 16, 16);
        g.drawImage(Images.heart, 5, 70, 16, 16);

        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.score), 30, 10);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.level), 30, 30);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d / %,d", Cubic.ENEMIES.size(), Cubic.level), 30, 50);
        drawNumberString(g, String.format(Translator.getLocale(), "%,d", Cubic.lives), 30, 70);
    }

    public void drawLevel(Graphics g)
    {
        Cubic.gameLevel.draw(g);
    }

    public void drawSky(Graphics g)
    {
        Paint paint = g.getGraphics2D().getPaint();

        g.getGraphics2D().setPaint(new GradientPaint(0, 0, SKY, 0, Cubic.game.getHeight(), SKY2));
        g.getGraphics2D().fillRect(0, 0, Cubic.game.getWidth(), Cubic.game.getHeight());

        g.getGraphics2D().setPaint(paint);
    }

    private void drawNumberString(Graphics g, String s, int x, int y)
    {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++)
        {
            g.drawImage(Images.numbers.get(chars[i]), x + i * 10, y, 8, 16);
        }
    }
}
