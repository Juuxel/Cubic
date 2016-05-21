package juuxel.cubic.level;

import juuxel.cubic.Cubic;
import juuxel.cubic.util.IBasicFunctions;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.util.render.Graphics;
import juuxel.cubic.util.sprite.Sprite;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Supplier;

public abstract class Level implements IBasicFunctions
{
    private static final Set<Supplier<Level>> SUPPLIERS = new HashSet<>();

    private Sprite ground;

    public Level(Sprite ground)
    {
        this.ground = ground;
    }

    public void draw(Graphics g)
    {
        for (int i = 0; i < Cubic.game.getWidth() / 32 + 1; i++)
        {
            int x = i * 32, y = (int) calculateY(32);

            g.drawImage(ground, x, y - 32, 32, 32);
        }

        drawDecoration(g);
    }

    public void drawDecoration(Graphics g)
    {}

    public static void registerDefaults()
    {
        registerLevelSupplier(LevelGrassyLands::new);
        registerLevelSupplier(LevelBrickCity::new);
    }

    public static void registerLevelSupplier(Supplier<Level> supplier)
    {
        SUPPLIERS.add(supplier);
    }

    public static Level getNewLevel()
    {
        return Randomizer.getRandomObject(SUPPLIERS).get();
    }
}
