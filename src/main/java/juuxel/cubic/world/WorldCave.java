/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.world;

import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.util.Utils;

import java.awt.*;
import java.util.concurrent.ThreadLocalRandom;

public final class WorldCave extends World
{
    private static final Color LIGHT_START = new Color(0, 255, 255, 180);
    private static final Color LIGHT_END = new Color(0, 255, 255, 0);
    private static final Color[] LIGHT_COLORS = new Color[] { LIGHT_START, LIGHT_END };
    private static final int LIGHT_RADIUS = 50;
    private static final AlphaComposite TRANSLUCENT_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5f);
    private static final AlphaComposite BG_CRYSTAL_COMPOSITE = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);

    private final Crystal[] crystals;
    private final BGCrystal[] backgroundCrystals;

    public WorldCave()
    {
        super(Images.stone);

        crystals = new Crystal[4 + ThreadLocalRandom.current().nextInt(3)];

        for (int i = 0; i < crystals.length; i++)
            crystals[i] = createCrystal();

        backgroundCrystals = new BGCrystal[8 + ThreadLocalRandom.current().nextInt(5)];

        for (int i = 0; i < backgroundCrystals.length; i++)
            backgroundCrystals[i] = createBackgroundCrystal();
    }

    private Crystal createCrystal()
    {
        int size = (4 + ThreadLocalRandom.current().nextInt(-1, 1)) * 12;
        int x = ThreadLocalRandom.current().nextInt(640 - size);

        return new Crystal(x, size);
    }

    private BGCrystal createBackgroundCrystal()
    {
        int x = ThreadLocalRandom.current().nextInt(640 - 32);
        int y = ThreadLocalRandom.current().nextInt(400 - 32);

        return new BGCrystal(x, y);
    }

    @Override
    public void drawSky(Graphics g)
    {
        for (int i = 0; i < GameWindow.getWidth() / 32 + 1; i++)
        {
            for (int j = 0; j < GameWindow.getHeight() / 32 + 1; j++)
            {
                int x = i * 32, y = (int) Utils.yOnScreen(j * 32);

                g.drawImage(Images.caveWall, x, y - 32, 32, 32);
            }
        }
    }

    @Override
    public void drawDecoration(Graphics g)
    {
        for (var crystal : crystals)
        {
            int y = (int) Utils.yOnScreen(64 + crystal.size);

            g.getGraphics2D().setComposite(TRANSLUCENT_COMPOSITE);
            g.getGraphics2D().setPaint(new RadialGradientPaint(crystal.x, y + crystal.size / 2, LIGHT_RADIUS,
                                                               new float[] { 0f, 1f },
                                                               LIGHT_COLORS));

            g.getGraphics2D().fillOval(crystal.x - LIGHT_RADIUS, y - LIGHT_RADIUS + crystal.size / 2,
                                       LIGHT_RADIUS * 2, LIGHT_RADIUS * 2);

            g.getGraphics2D().setComposite(AlphaComposite.SrcOver);
            g.getGraphics2D().setPaint(Color.BLACK);
            g.drawImage(Images.crystal.getImage(crystal),
                        crystal.x - crystal.size / 2, y,
                        crystal.size,
                        crystal.size);
        }

        for (var crystal : backgroundCrystals)
        {
            g.getGraphics2D().setComposite(BG_CRYSTAL_COMPOSITE);
            g.drawImage(Images.backgroundCrystal.getImage(crystal), crystal.x - 16, crystal.y - 16, 32, 32);
            g.getGraphics2D().setComposite(AlphaComposite.SrcOver);
        }
    }

    @Override
    public String getNameKey()
    {
        return "world.cave";
    }

    @Override
    protected void initEnemyList(EnemyList enemyList)
    {
        enemyList.registerEnemy(EnemyType.NORMAL, EnemyNormal::new);
        enemyList.registerEnemy(EnemyType.NORMAL, () -> new EnemyBird(EnemyBird.Type.FLY));
        enemyList.registerEnemy(EnemyType.RARE, EnemyFireMonster::new);
    }

    @Override
    public boolean isValidMenuBackground()
    {
        return false;
    }

    @Override
    public Color getTextColor()
    {
        return Color.WHITE;
    }

    private static final class Crystal
    {
        private final int x;
        private final int size;

        private Crystal(int x, int size)
        {
            this.x = x;
            this.size = size;
        }
    }

    private static final class BGCrystal
    {
        private final int x;
        private final int y; // A graphical Y coordinate, not a game Y coordinate

        private BGCrystal(int x, int y)
        {
            this.x = x;
            this.y = y;
        }
    }
}
