package juuxel.cubic.level;

import juuxel.cubic.Cubic;
import juuxel.cubic.util.IBasicFunctions;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.render.sprite.Sprite;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

/**
 * A Level is a "world" for Cubic. It sets the ground look,
 * decorations and other details of the current game.
 */
public abstract class Level implements IBasicFunctions
{
    private static final Set<Supplier<Level>> SUPPLIERS = new HashSet<>();

    private Sprite ground;

    /**
     * The class constructor.
     *
     * @param ground the ground sprite
     */
    public Level(Sprite ground)
    {
        this.ground = ground;
    }

    /**
     * Draws this level to the {@link Graphics} object.
     * The default implementation draws the ground and then
     * calls {@link #drawDecoration(Graphics)}. Subclasses don't
     * need call this.
     *
     * @param g the graphics object
     */
    public void draw(Graphics g)
    {
        for (int i = 0; i < Cubic.game.getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.drawImage(ground, x, y - 32, 32, 32);
        }

        drawDecoration(g);
    }

    /**
     * Draws the decoration of this level to the {@link Graphics} object.
     *
     * @param g the graphics object
     */
    public void drawDecoration(Graphics g)
    {}

    /**
     * An internal method to register Cubic's default levels.
     * Mods, please don't call this.
     */
    public static void registerDefaults()
    {
        registerLevelSupplier(LevelGrassyLands::new);
        registerLevelSupplier(LevelBrickCity::new);
    }

    /**
     * Registers a new Level Supplier to the game.
     * This method should be called on the coreInit phase.
     *
     * @param supplier the new supplier
     */
    public static void registerLevelSupplier(Supplier<Level> supplier)
    {
        SUPPLIERS.add(supplier);
    }

    /**
     * Gets a new level object from the supplier list.
     *
     * @return a Level
     * @see #registerLevelSupplier(Supplier) registerLevelSupplier
     */
    public static Level getNewLevel()
    {
        return Randomizer.getRandomObject(SUPPLIERS).get();
    }
}
