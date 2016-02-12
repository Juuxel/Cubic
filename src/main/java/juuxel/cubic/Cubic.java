package juuxel.cubic;

import juuxel.cubic.enemy.*;
import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.graphics.ISpriteHandler;
import juuxel.cubic.options.*;
import juuxel.cubic.reference.*;
import juuxel.cubic.util.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.BufferedWriter;
import java.io.PrintWriter;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic implements KeyListener
{
    public static Cubic game;
    public static Player player;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<AbstractEnemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 8;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();
    public static final List<ISpriteHandler> SPRITE_HANDLERS = new ArrayList<>();

    private final GameFrame gameFrame;

    public static final int START_SCREEN = 0;
    public static final int OPTIONS = 1;
    public static final int LANGUAGE_SCREEN = 2;
    public static final int CONTROLS = 3;

    private static int selectedButton = 0, selectedScreen = START_SCREEN, controlIndex = 0;
    private static boolean selectingBindings = false, optionsChanged = false, firstPaint = true;

    private Cubic()
    {
        (gameFrame = new GameFrame(this, Reference.NAME + " " + Reference.VERSION)).setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        Translator.initialize();
        Options.initialize();
        EnemyLists.initializeLists();
        game = new Cubic();
        player = new Player(Images.PLAYER);
        ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        while (inStartScreen)
        {
            game.repaint();
        }

        run();
    }

    public static void run() throws Exception
    {
        while (running)
        {
            CREATURES.forEach(Creature::logic);
            game.repaint();
            Thread.sleep(5);
        }
    }

    public void paint(Graphics g)
    {
        if (firstPaint && Options.font == null)
        {
            Options.font = g.getGraphics2D().getFont();
            firstPaint = false;
        }
        else
            g.getGraphics2D().setFont(Options.font);

        if (inStartScreen)
        {
            drawSky(g);
            drawGround(g);

            if (selectedScreen == START_SCREEN)
                drawList(g, Arrays.asList(Translator.translate("mainMenu.play"), Translator.translate("mainMenu.options"), Translator.translate("mainMenu.exit")));
            else if (selectedScreen == OPTIONS)
                drawList(g, Arrays.asList(Translator.translate("mainMenu.back"), Translator.translate("options.controls"), Translator.translate("options.languages")));
            else if (selectedScreen == LANGUAGE_SCREEN)
            {
                ArrayList<String> strings = new ArrayList<>();
                strings.add(Translator.translate("mainMenu.back"));
                strings.addAll(Translator.getLanguageNames());
                drawList(g, strings);
                drawButtons(g);
            }
            else if (selectedScreen == CONTROLS)
                drawControlScreen(g);

            g.drawImage(Images.LOGO, 10, 10, 128, 64);
            g.drawString(Translator.format("misc.version", Reference.VERSION), 10, 95);
        }
        else
        {
            drawSky(g);
            drawScore(g);
            drawGround(g);

            CREATURES.forEach(creature -> creature.draw(g));

            if (lives <= 0)
            {
                int dx = getWidth() / 2, dy = getHeight() / 2;
                g.getGraphics2D().drawImage(Images.GAME_OVER, dx - 64, dy - 32, dx + 64, dy + 32, 0, 0, 32, 16, null);
                running = false;
            }
        }
    }

    public void repaint()
    {
        gameFrame.repaint();
    }


    private void drawScore(Graphics g)
    {
        g.drawString(Translator.format("game.level", level), 10, 30);
        g.drawString(Translator.format("game.scoreToLevelUp", ENEMIES.size()), 10, 50);
        g.drawString(Translator.format("game.score", score), 10, 70);
        g.drawString(Translator.format("game.deaths", deaths), 10, 90);
        g.drawString(Translator.format("game.lives", lives), 10, 110);
    }

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.drawImage(Images.GRASS, x, y - 32, 32, 32);
        }
    }

    private void drawSky(Graphics g)
    {
        g.getGraphics2D().setColor(new Color(128, 218, 235));
        g.getGraphics2D().fillRect(0, 0, getWidth(), getHeight());
        g.getGraphics2D().setColor(Color.black);
    }

    private void drawList(Graphics g, List<String> entries)
    {
        int dx = getWidth() / 2 - 75, dy = getHeight() / 2 - 50;

        g.drawImage(Images.CURSOR, selectedScreen == LANGUAGE_SCREEN ? dx - 16 : dx, dy + selectedButton * 25, 16, 16);

        for (int i = 0; i < entries.size(); i++)
        {
            g.drawString(entries.get(i), dx + 16, dy + 12 + i * 25);
        }
    }

    private void drawButtons(Graphics g)
    {
        for (int i = 0; i < Translator.getLanguages().size(); i++)
        {
            int dx = getWidth() / 2 - 75, dy = getHeight() / 2 - 50;

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

    private String getControlText(KeyBinding binding, int index)
    {
        return controlIndex == index ? Translator.translate("controls.selecting") : Options.getKeyName(binding.getValue());
    }

    public void keyPressed(KeyEvent e)
    {
        if (!selectingBindings && e.getKeyCode() == Options.takeScreenshot.getValue())
            GraphicsUtilities.takeScreenshot();

        if (inStartScreen)
        {
            if (selectingBindings)
            {
                switch (controlIndex)
                {
                    case 1:
                        Options.moveLeft.setValue(e.getKeyCode());
                        break;
                    case 2:
                        Options.moveRight.setValue(e.getKeyCode());
                        break;
                    case 3:
                        Options.jump.setValue(e.getKeyCode());
                        break;
                    case 4:
                        Options.takeScreenshot.setValue(e.getKeyCode());
                }

                selectingBindings = false;
                controlIndex = 0;
                optionsChanged = true;
            }
            else
            {
                switch (e.getKeyCode())
                {
                    case VK_UP:
                        if (selectedButton != 0)
                            selectedButton--;
                        else
                            selectedButton = getMaximumIndex(selectedScreen);
                        break;
                    case VK_DOWN:
                        if (selectedButton != getMaximumIndex(selectedScreen))
                            selectedButton++;
                        else
                            selectedButton = 0;
                        break;
                    case VK_SPACE:
                    case VK_ENTER:
                        selectButton();
                        break;
                }
            }
        }
        else
        {
            int key = e.getKeyCode();

            if (key == Options.moveLeft.getValue())
            {
                player.xSpeed = Math.max(-1.75, player.xSpeed - 1.5);
                moveKeyDown = true;
            }
            else if (key == Options.moveRight.getValue())
            {
                player.xSpeed = Math.min(1.75, player.xSpeed + 1.5);
                moveKeyDown = true;
            }
            else if (key == Options.jump.getValue())
                jumpKeyDown = true;
        }
    }

    public void keyReleased(KeyEvent e)
    {
        if (e.getKeyCode() == Options.moveLeft.getValue() || e.getKeyCode() == Options.moveRight.getValue())
            moveKeyDown = false;
        else if (e.getKeyCode() == Options.jump.getValue())
            jumpKeyDown = false;
    }

    public void keyTyped(KeyEvent e)
    {}

    public int getHeight()
    {
        return gameFrame.getHeight();
    }

    public int getWidth()
    {
        return gameFrame.getWidth();
    }

    public GameFrame getGameFrame()
    {
        return gameFrame;
    }

    public static double calculateY(double y1)
    {
        return game.getHeight() - y1;
    }

    public static void exit()
    {
        System.exit(0);
    }

    public static int getMaximumIndex(int screen)
    {
        switch (screen)
        {
            case START_SCREEN:
                return 2;
            case OPTIONS:
                return 2;
            case LANGUAGE_SCREEN:
                return Translator.getLanguages().size();
            case CONTROLS:
                return 4;
            default:
                return 0;
        }
    }

    private static void selectButton()
    {
        switch (selectedScreen)
        {
            case START_SCREEN:
                if (selectedButton == 0)
                {
                    SPRITE_HANDLERS.forEach(ISpriteHandler::onSpriteBake);
                    inStartScreen = false;
                }
                else if (selectedButton == 1)
                    selectScreen(OPTIONS);
                else
                    exit();
                break;
            case OPTIONS:
                if (selectedButton == 0)
                {
                    if (optionsChanged)
                    {
                        Options.reloadAndWriteOptions();
                        optionsChanged = false;
                    }

                    selectScreen(START_SCREEN);
                }
                else if (selectedButton == 1)
                    selectScreen(CONTROLS);
                else if (selectedButton == 2)
                    selectScreen(LANGUAGE_SCREEN);
                break;
            case LANGUAGE_SCREEN:
                if (selectedButton == 0)
                    selectScreen(OPTIONS);
                else
                {
                    Translator.setLanguage(selectedButton - 1);
                    Translator.reloadProperties();
                    optionsChanged = true;
                }
                break;
            case CONTROLS:
                if (selectedButton == 0)
                    selectScreen(OPTIONS);
                else
                {
                    selectingBindings = true;
                    controlIndex = selectedButton;
                }
                break;
        }
    }

    public static void selectScreen(int screen)
    {
        selectedScreen = screen;
        selectedButton = 0;
    }

    public static final class GameFrame extends JFrame
    {
        private GameFrame(Cubic game, String title)
        {
            super(title);
            setContentPane(new WindowPane(game));
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            SwingUtilities.invokeLater(() -> setSize(640, 480));
            addKeyListener(game);
        }
    }

    private static class WindowPane extends JPanel
    {
        private final Cubic game;

        WindowPane(Cubic game)
        {
            this.game = game;
        }

        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            game.paint(Graphics.fromGraphics2D((Graphics2D) g));
        }
    }
}
