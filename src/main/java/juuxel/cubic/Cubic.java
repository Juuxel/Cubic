/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic;

import juuxel.cubic.creature.Creature;
import juuxel.cubic.creature.Player;
import juuxel.cubic.creature.enemy.Enemy;
import juuxel.cubic.creature.enemy.EnemyType;
import juuxel.cubic.lib.Images;
import juuxel.cubic.mod.ModLoader;
import juuxel.cubic.options.Options;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.sprite.SpriteLoader;
import juuxel.cubic.util.Translator;
import juuxel.cubic.world.World;

import java.awt.CardLayout;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

public final class Cubic
{
    public static Player player;
    public static World world;
    public static boolean inStartScreen = true, running = true, moveKeyDown, jumpKeyDown;
    public static final List<Creature> CREATURES = new CopyOnWriteArrayList<>();
    public static final List<Enemy> ENEMIES = new CopyOnWriteArrayList<>();
    public static final List<Enemy> COLLIDING_ENEMIES = new CopyOnWriteArrayList<>();

    private static boolean hasTimerBeenCreated = false;
    private static int tick = 0;

    private Cubic() throws Exception { throw new Exception("This class cannot be instantiated"); }

    public static void main(String[] args)
    {
        processArgs(args);
        init();
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

    /**
     * Initialize the core components of Cubic, including the translator, options, and the sprite system.
     */
    private static void init()
    {
        SpriteLoader.registerDefaults();
        Images.init();
        World.registerDefaults();
        world = World.getInstance(World.DEFAULT_WORLD);
        ModLoader.load();
        ModLoader.init();
        Translator.init();
        Options.init();
        GameWindow.init();

        new java.util.Timer().scheduleAtFixedRate(new TimerTask()
        {
            @Override
            public void run()
            {
                GameWindow.repaint();
                tick = (tick + 1) % 60;
            }
        }, 0L, 1000L / (long) Options.fps.getValue());
    }

    /**
     * Starts a new game in {@code world}.
     * Sets {@link #player} to a new player and clears all creatures and enemies.
     * @param world the world
     */
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
            hasTimerBeenCreated = true;
        }
    }

    public static void selectScreen(String screen)
    {
        CardLayout layout = GameWindow.getWindowPane().getLayout();

        layout.show(GameWindow.getWindowPane(), screen);
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
}
