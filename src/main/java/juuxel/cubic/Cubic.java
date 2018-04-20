/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.creature.Player;
import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.level.Level;
import juuxel.cubic.menu.AboutScreen;
import juuxel.cubic.menu.LevelMenu;
import juuxel.cubic.menu.MainMenu;
import juuxel.cubic.menu.OptionsMenu;
import juuxel.cubic.mod.ModLoader;
import juuxel.cubic.options.*;
import juuxel.cubic.lib.*;
import juuxel.cubic.util.*;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.Screenshooter;
import juuxel.cubic.render.RenderEngine;
import juuxel.cubic.render.sprite.SpriteLoader;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic
{
    public static Cubic game;
    public static Player player;
    public static Level gameLevel;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<Enemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static final List<Enemy> COLLIDING_ENEMIES = new CopyOnWriteArrayList<>();
    public static int score = 0, deaths = 0, level = 1, lives = 3;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();
    public static final List<CreatureListener> CREATURE_LISTENERS = new ArrayList<>();

    private final GameFrame gameFrame;
    private final WindowPane windowPane;
    private final GamePane gamePane;

    private static boolean hasTimerBeenCreated = false;
    private static int tick = 0;

    private Cubic()
    {
        gamePane = new GamePane();
        windowPane = new WindowPane(this);
        (gameFrame = new GameFrame(this, GameInfo.NAME + " " + GameInfo.VERSION)).setVisible(true);
        gamePane.requestFocusInWindow();
    }

    public static void main(String[] args)
    {
        processArgs(args);

        coreInit();
        contentInit();
        game = new Cubic();
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
    private static void coreInit()
    {
        Translator.init();
        Options.init();
        SpriteLoader.registerDefaults();
        Images.init();
        Level.registerDefaults();
        gameLevel = Level.getRandomLevel();
        ModLoader.load();
        ModLoader.coreInit();
    }

    private static void contentInit()
    {
        EnemyLists.initLists();
        ModLoader.contentInit();
    }

    public static void newGame(Level level)
    {
        score = 0;
        deaths = 0;
        Cubic.level = 1;
        lives = 3;
        CREATURES.clear();
        ENEMIES.clear();
        player = new Player();
        addEnemy(EnemyLists.createEnemy(EnemyType.NORMAL));
        Cubic.gameLevel = level;
        Cubic.inStartScreen = false;
        Cubic.selectScreen("Game");

        if (!hasTimerBeenCreated)
        {
            new java.util.Timer().scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!inStartScreen && lives > 0)
                    {
                        CREATURES.forEach(Creature::executeLogic);
                    }
                }
            }, 0L, 5L);

            new java.util.Timer().scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!inStartScreen)
                    {
                        RenderEngine.repaint();
                        tick = (tick + 1) % 60;
                    }
                }
            }, 0L, 1000L / (long) Options.fps);
            hasTimerBeenCreated = true;
        }
    }

    public void repaint()
    {
        gameFrame.repaint();
    }

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

    public static void selectScreen(String screen)
    {
        CardLayout layout = game.windowPane.layout;

        layout.show(game.windowPane, screen);
    }

    public static int getTick()
    {
        return tick / 3;
    }

    public static void addEnemy(Enemy enemy)
    {
        ENEMIES.add(enemy);
        COLLIDING_ENEMIES.add(enemy);
    }

    private static final class GameFrame extends JFrame
    {
        private GameFrame(Cubic game, String title)
        {
            super(title);
            setContentPane(game.windowPane);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(640, 480);
            setIconImage(Images.ICON);
        }
    }

    public static class WindowPane extends JPanel
    {
        private final CardLayout layout;

        private WindowPane(Cubic game)
        {
            layout = new CardLayout();
            setLayout(layout);
            add(new MainMenu(), "MainMenu");
            add(new OptionsMenu(), "OptionsMenu");
            add(new LevelMenu(), "LevelMenu");
            add(new AboutScreen(), "AboutScreen");
            add(game.gamePane, "Game");
            layout.show(this, "MainMenu");
            addKeyListener(new Keyboard());
            setFocusable(true);
        }

        @Override
        protected void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);

            RenderEngine.drawSky(Graphics.fromAWTGraphics(g));
            RenderEngine.drawLevel(Graphics.fromAWTGraphics(g));
        }
    }

    public static class GamePane extends JPanel
    {
        private GamePane()
        {
        }

        @Override
        public void paintComponent(java.awt.Graphics g)
        {
            super.paintComponent(g);
            RenderEngine.repaint(Graphics.fromAWTGraphics(g));
        }
    }

    private static class Keyboard extends KeyAdapter
    {
        public void keyPressed(KeyEvent e)
        {
            if (e.getKeyCode() == Options.takeScreenshot.getValue())
                Screenshooter.takeScreenshot();
            else if (!inStartScreen)
            {
                int key = e.getKeyCode();

                if (key == Options.moveLeft.getValue())
                {
                    player.moveLeft();
                    moveKeyDown = true;
                }
                else if (key == Options.moveRight.getValue())
                {
                    player.moveRight();
                    moveKeyDown = true;
                }
                else if (key == Options.jump.getValue())
                    jumpKeyDown = true;
                else if (key == VK_ESCAPE)
                {
                    inStartScreen = true;
                    MainMenu.continueButton.setVisible(true);
                    selectScreen("MainMenu");
                }
            }
        }

        public void keyReleased(KeyEvent e)
        {
            if (e.getKeyCode() == Options.moveLeft.getValue() || e.getKeyCode() == Options.moveRight.getValue())
                moveKeyDown = false;
            else if (e.getKeyCode() == Options.jump.getValue())
                jumpKeyDown = false;
        }

    }
}
