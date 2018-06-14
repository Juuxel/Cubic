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
import juuxel.cubic.world.World;
import juuxel.cubic.menu.AboutScreen;
import juuxel.cubic.menu.WorldMenu;
import juuxel.cubic.menu.MainMenu;
import juuxel.cubic.menu.OptionsMenu;
import juuxel.cubic.mod.ModLoader;
import juuxel.cubic.options.*;
import juuxel.cubic.lib.*;
import juuxel.cubic.util.*;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.Screenshots;
import juuxel.cubic.render.RenderEngine;
import juuxel.cubic.render.sprite.SpriteLoader;
import juuxel.cubic.world.WorldGrassyLands;

import javax.swing.*;
import java.awt.CardLayout;
import java.awt.event.*;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

import static java.awt.event.KeyEvent.*;

public final class Cubic
{
    public static Player player;
    public static World world;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();
    public static final List<Enemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static final List<Enemy> COLLIDING_ENEMIES = new CopyOnWriteArrayList<>();

    private static GamePane gamePane;
    private static WindowPane windowPane;
    private static GameFrame gameFrame;

    private static boolean hasTimerBeenCreated = false;
    private static int tick = 0;

    private Cubic() throws Exception { throw new Exception("This class cannot be instantiated"); }

    public static void main(String[] args)
    {
        processArgs(args);

        init();
        initGui();
    }

    private static void initGui()
    {
        gamePane = new GamePane();
        windowPane = new WindowPane();
        gameFrame = new GameFrame(String.format("%s %s", GameInfo.NAME, GameInfo.VERSION));

        SwingUtilities.invokeLater(() -> {
            gameFrame.setVisible(true);
            gamePane.requestFocusInWindow();
        });
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
    private static void init()
    {
        SpriteLoader.registerDefaults();
        Images.init();
        World.registerDefaults();
        world = World.getInstance(WorldGrassyLands.class);
        ModLoader.load();
        ModLoader.init();
        Translator.init();
        Options.init();
    }

    public static void newGame(World world)
    {
        CREATURES.clear();
        ENEMIES.clear();
        player = new Player();
        Cubic.world = world;
        addEnemy(world.getEnemyList().createEnemy(EnemyType.NORMAL));
        Cubic.inStartScreen = false;
        Cubic.selectScreen("Game");

        if (!hasTimerBeenCreated)
        {
            new java.util.Timer().scheduleAtFixedRate(new TimerTask()
            {
                @Override
                public void run()
                {
                    if (!inStartScreen && player.lives > 0)
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
                        Cubic.repaint();
                        tick = (tick + 1) % 60;
                    }
                }
            }, 0L, 1000L / (long) Options.fps);
            hasTimerBeenCreated = true;
        }
    }

    public static void repaint()
    {
        gameFrame.repaint();
    }

    public static int getHeight()
    {
        return gameFrame.getHeight();
    }

    public static int getWidth()
    {
        return gameFrame.getWidth();
    }

    public static GameFrame getGameFrame()
    {
        return gameFrame;
    }

    public static void selectScreen(String screen)
    {
        CardLayout layout = windowPane.layout;

        layout.show(windowPane, screen);
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
        private GameFrame(String title)
        {
            super(title);
            setContentPane(windowPane);
            setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            setSize(640, 480);
            setIconImage(Images.ICON);
        }
    }

    public static class WindowPane extends JPanel
    {
        private final CardLayout layout;

        private WindowPane()
        {
            layout = new CardLayout();
            setLayout(layout);
            add(new MainMenu(), "MainMenu");
            add(new OptionsMenu(), "OptionsMenu");
            add(new WorldMenu(), "WorldMenu");
            add(new AboutScreen(), "AboutScreen");
            add(gamePane, "Game");
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
                Screenshots.takeScreenshot();
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
