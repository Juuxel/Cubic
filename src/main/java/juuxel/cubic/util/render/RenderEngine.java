package juuxel.cubic.util.render;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.options.KeyBinding;
import juuxel.cubic.options.Options;
import juuxel.cubic.util.Translator;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Paint;
import java.util.*;

import static juuxel.cubic.Cubic.START_SCREEN;
import static juuxel.cubic.Cubic.OPTIONS;
import static juuxel.cubic.Cubic.LANGUAGE_SCREEN;
import static juuxel.cubic.Cubic.CONTROLS;

public class RenderEngine
{
    public static final Color SKY = new Color(0x80daeb);
    public static final Color SKY2 = new Color(0x7ceeeb);

    private boolean firstPaint = true;

    private RenderEngine()
    {}

    public static RenderEngine INSTANCE = new RenderEngine();

    public void paint(Graphics g)
    {
        if (firstPaint && Options.font == null)
        {
            Options.font = g.getGraphics2D().getFont();
            firstPaint = false;
        }
        else
            g.getGraphics2D().setFont(Options.font);

        if (Cubic.inStartScreen)
        {
            drawSky(g);
            drawGround(g);

            if (Cubic.getSelectedScreen() == START_SCREEN)
                drawList(g, Arrays.asList(Translator.translate("mainMenu.play"), Translator.translate("mainMenu.options"), Translator.translate("mainMenu.exit")));
            else if (Cubic.getSelectedScreen() == OPTIONS)
                drawList(g, Arrays.asList(Translator.translate("mainMenu.back"), Translator.translate("options.controls"), Translator.translate("options.languages")));
            else if (Cubic.getSelectedScreen() == LANGUAGE_SCREEN)
            {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(Translator.translate("mainMenu.back"));
                strings.addAll(Translator.getLanguageNames());
                drawList(g, strings);
                drawButtons(g);
            }
            else if (Cubic.getSelectedScreen() == CONTROLS)
                drawControlScreen(g);

            g.drawImage(Images.LOGO, 10, 10, 128, 64);
            drawVersion(g);
        }
        else
        {
            drawSky(g);
            drawScore(g);
            drawGround(g);

            Cubic.CREATURES.forEach(creature -> creature.draw(g));

            if (Cubic.lives <= 0)
            {
                int dx = Cubic.game.getWidth() / 2, dy = Cubic.game.getHeight() / 2;
                g.getGraphics2D().drawImage(Images.GAME_OVER, dx - 64, dy - 32, dx + 64, dy + 32, 0, 0, 32, 16, null);
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

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < Cubic.game.getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) Cubic.calculateY(32);

            g.drawImage(Images.GRASS, x, y - 32, 32, 32);
        }
    }

    private void drawSky(Graphics g)
    {
        Paint paint = g.getGraphics2D().getPaint();

        g.getGraphics2D().setPaint(new GradientPaint(0, 0, SKY, 0, Cubic.game.getHeight(), SKY2));
        g.getGraphics2D().fillRect(0, 0, Cubic.game.getWidth(), Cubic.game.getHeight());

        g.getGraphics2D().setPaint(paint);
    }

    private void drawList(Graphics g, java.util.List<String> entries)
    {
        int dx = Cubic.game.getWidth() / 2 - 75, dy = Cubic.game.getHeight() / 2 - 50;

        g.drawImage(Images.CURSOR, Cubic.getSelectedScreen() == LANGUAGE_SCREEN ? dx - 16 : dx, dy + Cubic.getSelectedButton() * 25, 16, 16);

        for (int i = 0; i < entries.size(); i++)
        {
            g.drawString(entries.get(i), dx + 16, dy + 12 + i * 25);
        }
    }

    private void drawButtons(Graphics g)
    {
        for (int i = 0; i < Translator.getLanguages().size(); i++)
        {
            int dx = Cubic.game.getWidth() / 2 - 75, dy = Cubic.game.getHeight() / 2 - 50;

            int selectedIndex = Translator.getLanguageIndex();

            g.drawImage(selectedIndex == i ? Images.SELECTED_BUTTON : Images.BUTTON, dx, dy + (i + 1) * 25, 16, 15);
        }
    }

    private void drawControlScreen(Graphics g)
    {
        ArrayList<String> values = new ArrayList<>();
        values.add(Translator.translate("mainMenu.back"));
        values.add(Translator.format("controls.moveLeft", getControlText(Options.moveLeft, 1)));
        values.add(Translator.format("controls.moveRight", getControlText(Options.moveRight, 2)));
        values.add(Translator.format("controls.jump", getControlText(Options.jump, 3)));
        values.add(Translator.format("controls.screenshot", getControlText(Options.takeScreenshot, 4)));
        drawList(g, values);
    }

    private void drawVersion(Graphics g)
    {
        char[] version = GameInfo.VERSION.toCharArray();

        for (int i = 0; i < version.length; i++)
        {
            g.drawImage(Images.numbers.get(version[i]), 10 + i * 8, 95, 8, 16);
        }
    }

    private void drawNumberString(Graphics g, String s, int x, int y)
    {
        char[] chars = s.toCharArray();

        for (int i = 0; i < chars.length; i++)
        {
            g.drawImage(Images.numbers.get(chars[i]), x + i * 10, y, 8, 16);
        }
    }

    private String getControlText(KeyBinding binding, int index)
    {
        return Cubic.getControlIndex() == index ? Translator.translate("controls.selecting") : Options.getKeyName(binding.getValue());
    }
}
