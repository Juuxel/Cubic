/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.world;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.Randomizer;
import juuxel.cubic.util.Utils;

import java.util.*;

public final class WorldGrassyLands extends World
{
    private final Cloud[] clouds;

    private static final Random RANDOM = new Random();

    public WorldGrassyLands()
    {
        super(Images.grass);

        clouds = new Cloud[RANDOM.nextInt(2) + 3];

        for (int i = 0; i < clouds.length; i++)
        {
            newCloud(i);
        }
    }

    private void newCloud(int i)
    {
        int x = RANDOM.nextInt(640 - i * 40) + i * 40;
        int y = RANDOM.nextInt(180) + 300;
        int size = RANDOM.nextInt(3) + 1;
        float alpha = Math.max(RANDOM.nextFloat(), 0.3F);
        float speed = RANDOM.nextFloat() + 0.05F;
        Direction direction = Randomizer.getRandomObject(Direction.LEFT, Direction.RIGHT);
        clouds[i] = new Cloud(x, y, size, i, alpha, speed, direction);
    }

    @Override
    public String getNameKey()
    {
        return "world.grassyLands";
    }

    @Override
    public void drawDecoration(Graphics g)
    {
        super.drawDecoration(g);

        for (Cloud cloud : clouds)
        {
            g.drawImageWithAlpha(Images.cloud.getImage(cloud),
                                 (int) cloud.x,
                                 (int) Utils.yOnScreen(cloud.y),
                                 80 * cloud.size,
                                 40 * cloud.size,
                                 cloud.baseAlpha * cloud.lifeAlpha);

            cloud.tick();
        }
    }

    @Override
    protected void initEnemyList(EnemyList enemyList)
    {
        enemyList.registerEnemy(EnemyType.NORMAL, EnemyNormal::new);
        enemyList.registerEnemy(EnemyType.NORMAL, EnemyBird::new);
        enemyList.registerEnemy(EnemyType.RARE, EnemyBouncing::new);
    }

    private final class Cloud
    {
        float x;
        final int y, size, index;
        final float baseAlpha, speed;
        final Direction direction;
        float lifeAlpha = 0.1F;
        private boolean initializing = true;

        private Cloud(int x, int y, int size, int index, float baseAlpha, float speed, Direction direction)
        {
            this.x = x;
            this.y = y;
            this.size = size;
            this.index = index;
            this.baseAlpha = baseAlpha;
            this.speed = speed;
            this.direction = direction;
        }

        void tick()
        {
            if (initializing)
            {
                lifeAlpha += 0.1F;

                if (lifeAlpha >= 1.0F)
                    initializing = false;
                else
                    return;
            }

            x += (direction == Direction.LEFT ? -speed : speed);

            if (x < 0 || x > Cubic.game.getWidth())
            {
                lifeAlpha -= 0.1F;
            }

            if (lifeAlpha <= 0.0F)
                newCloud(index);
        }
    }
}
