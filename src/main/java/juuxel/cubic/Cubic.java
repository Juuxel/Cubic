package juuxel.cubic;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.creature.Player;
import juuxel.cubic.creature.enemy.Enemy;
import juuxel.cubic.creature.enemy.EnemyLists;
import juuxel.cubic.creature.enemy.EnemyType;
import juuxel.cubic.level.Level;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.mod.ModLoader;
import juuxel.cubic.options.Options;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.RenderEngine;
import juuxel.cubic.render.Screenshooter;
import juuxel.cubic.render.sprite.SpriteLoader;
import juuxel.cubic.util.ICreatureListener;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic implements KeyListener
{
    public static Cubic game;
    public static Player player;
    public static Level gameLevel;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<Enemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 8;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();
    public static final List<ICreatureListener> CREATURE_LISTENERS = new ArrayList<>();

    private final GameFrame gameFrame;

    public static final int START_SCREEN = 0;
    public static final int OPTIONS = 1;
    public static final int LANGUAGE_SCREEN = 2;
    public static final int CONTROLS = 3;

    private static int selectedButton = 0, selectedScreen = START_SCREEN, controlIndex = 0;
    private static boolean selectingBindings = false, optionsChanged = false;

    private static ModLoader modLoader;

    private Cubic()
    {
        (gameFrame = new GameFrame(this, GameInfo.NAME + " " + GameInfo.VERSION)).setVisible(true);
    }

    public static void main(String[] args) throws Exception
    {
        processArgs(args);

        coreInit();
        contentInit();
        game = new Cubic();
        gameLevel = Level.getNewLevel();
        player = new Player();
        ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        while (inStartScreen)
        {
            RenderEngine.repaint();
        }

        run();
    }

    private static void processArgs(String[] args)
    {
        for (String arg : args)
        {
            if (arg.equals("-h") || arg.equals("--help"))
            {
                System.out.println("Usage: cubic [-h | --help]");
                System.out.println("Options:");
                System.out.println("-h, --help          Prints this message and exits.");
                System.exit(0);
            }
        }
    }

    /* Initialize the core components of Cubic. (Translator, options, sprite system)
     */
    private static void coreInit() throws Exception
    {
        modLoader = new ModLoader();
        Translator.initialize();
        Options.initialize();
        SpriteLoader.registerDefaults();
        Level.registerDefaults();
        modLoader.init();
        modLoader.coreInit();
    }

    private static void contentInit()
    {
        EnemyLists.initializeLists();
        Images.initialize();
        modLoader.contentInit();
    }

    public static void run() throws Exception
    {
        while (running)
        {
            CREATURES.forEach(Creature::executeLogic);
            RenderEngine.repaint();
            Thread.sleep(5);
        }
    }

    public void repaint()
    {
        gameFrame.repaint();
    }

    public void keyPressed(KeyEvent e)
    {
        if (!selectingBindings && e.getKeyCode() == Options.takeScreenshot.getValue())
            Screenshooter.takeScreenshot();

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
                player.setXSpeed(Math.max(-1.75, player.getXSpeed() - 1.5));
                moveKeyDown = true;
            }
            else if (key == Options.moveRight.getValue())
            {
                player.setXSpeed(Math.min(1.75, player.getXSpeed() + 1.5));
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

    public static int getSelectedButton()
    {
        return selectedButton;
    }

    public static int getSelectedScreen()
    {
        return selectedScreen;
    }

    public static int getControlIndex()
    {
        return controlIndex;
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
            setContentPane(new WindowPane());
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(640, 480);
            addKeyListener(game);
        }
    }

    private static class WindowPane extends JPanel
    {
        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            RenderEngine.INSTANCE.repaint(Graphics.fromAWTGraphics(g));
        }
    }
}
