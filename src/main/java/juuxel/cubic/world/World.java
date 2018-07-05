/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.world;

import juuxel.cubic.creature.enemy.EnemyList;
import juuxel.cubic.menu.CubicLookAndFeel;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.RenderEngine;
import juuxel.cubic.render.sprite.Sprite;
import juuxel.cubic.util.Sounds;
import juuxel.cubic.util.Utils;

import javax.sound.sampled.Clip;
import java.awt.Color;
import java.util.*;

/**
 * Worlds set the ground look, decorations, enemies and other details of the current game.
 */
public abstract class World
{
    public static final List<World> WORLDS = new ArrayList<>();

    /**
     * The default world of the game, currently {@link WorldGrassyLands}.
     */
    public static final Class<? extends World> DEFAULT_WORLD = WorldGrassyLands.class;

    private final EnemyList enemyList = new EnemyList();
    private Sprite ground;

    /**
     * The class constructor.
     *
     * @param ground the ground sprite
     */
    public World(Sprite ground)
    {
        this.ground = ground;
        initEnemyList(enemyList);
    }

    /**
     * Draws this world to the {@link Graphics} object.
     * The implementation calls {@link #drawDecoration(Graphics)} and draws the ground
     * in the order specified by {@link #isGroundBehindDecorations()}.
     * Subclasses don't need to call this.
     *
     * @param g the graphics object
     */
    public final void draw(Graphics g)
    {
        if (isGroundBehindDecorations())
        {
            drawGround(g);
            drawDecoration(g);
        }
        else
        {
            drawDecoration(g);
            drawGround(g);
        }
    }

    private void drawGround(Graphics g)
    {
        for (int i = 0; i < GameWindow.getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) Utils.yOnScreen(32);

            g.drawImage(ground.getImage(i), x, y - 32, 32, 32);
        }
    }

    /**
     * Draws the decoration of this world to the {@link Graphics} object.
     *
     * @param g the graphics object
     */
    public void drawDecoration(Graphics g)
    {}

    /**
     * Draws the sky of this world to the {@link Graphics} object.
     *
     * @param g the graphics object
     */
    public void drawSky(Graphics g)
    {
        RenderEngine.drawSky(g);
    }

    /**
     * Gets the translation key of the name of this world.
     *
     * @return the key
     */
    public abstract String getNameKey();

    /**
     * Initializes the enemy list of this level.
     *
     * @param enemyList the enemy list
     */
    protected abstract void initEnemyList(EnemyList enemyList);

    /**
     * Gets the loop that plays when the player plays in this world.
     *
     * @return a looping {@code Clip}
     */
    public Clip getMusicLoop()
    {
        return Sounds.GRASSY_LANDS_LOOP;
    }

    /**
     * Gets the {@link EnemyList enemy list} of this level.
     *
     * @return the enemy list
     */
    public EnemyList getEnemyList()
    {
        return enemyList;
    }

    /**
     * An internal method to register Cubic's default worlds.
     */
    public static void registerDefaults()
    {
        registerWorld(new WorldGrassyLands());
        registerWorld(new WorldBrickCity());
        registerWorld(new WorldCave());
    }

    /**
     * Registers a new World to the game.
     *
     * @param world the new world
     */
    public static void registerWorld(World world)
    {
        if (!WORLDS.contains(world))
            WORLDS.add(world);
    }

    /**
     * Gets the world instance of {@code worldClass}.
     *
     * @param worldClass the class
     * @return the world instance
     * @throws NoSuchElementException if instance is not found
     */
    public static World getInstance(Class<? extends World> worldClass) throws NoSuchElementException
    {
        return WORLDS.stream()
                     .filter(w -> w.getClass() == worldClass)
                     .findFirst()
                     .orElseThrow(NoSuchElementException::new);
    }

    /**
     * Gets the text color of the interface and the point popups.
     *
     * @return the text color
     */
    public Color getTextColor()
    {
        return CubicLookAndFeel.TEXT_COLOR;
    }

    /**
     * Returns true if this world can be used as a background for the {@link juuxel.cubic.menu.MainMenu main menu}.
     *
     * @return true if this world is a valid background for the main menu
     */
    public boolean isValidMenuBackground()
    {
        return true;
    }

    /**
     * Returns true if the ground is drawn behind decorations.
     * Controls the behavior of {@link #draw}.
     *
     * @return true if the ground is drawn behind decorations
     */
    public boolean isGroundBehindDecorations()
    {
        return false;
    }
}
