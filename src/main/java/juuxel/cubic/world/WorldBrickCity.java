/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.world;

import juuxel.cubic.lib.Images;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.util.Utils;

import java.awt.Image;

public final class WorldBrickCity extends World
{
    private final Building[] buildings;

    public WorldBrickCity()
    {
        super(Images.bricks);

        buildings = new Building[Randomizer.RANDOM.nextInt(4) + 4];

        for (int i = 0; i < buildings.length; i++)
            newBuilding(i);
    }

    @Override
    public String getNameKey()
    {
        return "world.brickCity";
    }

    private void newBuilding(int i)
    {
        int x = Randomizer.RANDOM.nextInt(300) - 150 + i * (640 / buildings.length);
        int size = Randomizer.RANDOM.nextInt(3) + 5 - i / 2;
        buildings[i] = new Building(x, size);
    }

    @Override
    public void drawDecoration(Graphics g)
    {
        super.drawDecoration(g);

        for (Building building : buildings)
        {
            Image image = Images.building.getImage(building);

            g.drawImageWithAlpha(image,
                                 building.x,
                                 (int) Utils.yOnScreen(image.getHeight(null) * building.size + 64),
                                 image.getWidth(null) * building.size,
                                 image.getHeight(null) * building.size,
                                 Math.min(0.5F * (float) building.size / 3.0F, 1.0F));
        }
    }

    private final class Building
    {
        final int x, size;

        private Building(int x, int size)
        {
            this.x = x;
            this.size = size;
        }
    }
}