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

import java.awt.Color;

public class WorldMysteriousCave extends World
{
    private static final Color BG_TOP = new Color(0x444444);
    private static final Color BG_BOTTOM = new Color(0x4F4F4F);

    public WorldMysteriousCave()
    {
        super(Images.stone);
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
}
