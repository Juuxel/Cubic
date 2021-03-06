/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.enemy.Enemy;
import juuxel.cubic.creature.enemy.EnemyType;
import juuxel.cubic.creature.fx.EffectDeath;
import juuxel.cubic.creature.fx.EffectLevelUp;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.render.GameWindow;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.util.Direction;
import juuxel.cubic.util.Sounds;

import java.awt.AlphaComposite;

public final class Player extends Creature
{
    private static final int MAX_INVINCIBLE_TIME = 200;
    private int invincibleTime = 0;
    private boolean jumpPressed = false;
    private boolean jumpWasPressed = false;

    public int score = 0;
    public int deaths = 0;
    public int level = 1;
    public int lives = 3;

    public Player()
    {
        collidesWithGround = true;
        flippingEnabled = true;
        setSprite(Images.player);
        x = 100;
        y = 100;
        speedModifierY = 2;
    }

    @Override
    public void draw(Graphics g)
    {
        if (invincibleTime > 0)
        {
            float alpha = 1.0f - ((float) invincibleTime) / ((float) MAX_INVINCIBLE_TIME);
            g.getGraphics2D().setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, alpha));
        }

        drawCreature(g, getSprite());

        if (invincibleTime > 0)
            g.getGraphics2D().setComposite(AlphaComposite.SrcOver);
    }

    @Override
    protected void logic()
    {
        ySpeed -= 0.1;

        if (onGround())
        {
            ySpeed = 0;
            y = GameValues.GROUND;

            if (jumpPressed && !jumpWasPressed) ySpeed = 7;

            if (xSpeed != 0 && !Cubic.moveKeyDown) xSpeed *= 0.97;
            else if (Cubic.moveKeyDown) Cubic.moveKeyDown = false;
        }

        if ((x < -10 && xSpeed < 0) || (x > GameWindow.getWidth() && xSpeed > 0))
            xSpeed = -xSpeed;

        jumpWasPressed = jumpPressed;
        jumpPressed = Cubic.jumpKeyDown;

        if (invincibleTime > 0)
            invincibleTime--;

        for (Enemy enemy : Cubic.COLLIDING_ENEMIES)
        {
            if (!enemy.collidesWithPlayer) // Do nothing to dead / other non-colliding enemies
                continue;

            // Modified from https://gamedev.stackexchange.com/a/29796

            float w = 0.5F * (spriteWidth + enemy.spriteWidth);
            float h = 0.5F * (spriteHeight + enemy.spriteHeight);
            float dx = (float) (x + spriteWidth / 2 - (enemy.x + enemy.spriteWidth / 2));
            float dy = (float) (y + spriteHeight / 2 - (enemy.y + enemy.spriteHeight / 2));

            if (Math.abs(dx) <= w && Math.abs(dy) <= h)
            {
                float wy = w * dy;
                float hx = h * dx;

                if (wy > hx)
                {
                    if (wy > -hx) // Top
                        enemy.onCollidedWithPlayer(Direction.UP);
                    else if (invincibleTime == 0) // Left
                        enemy.onCollidedWithPlayer(Direction.LEFT);
                }
                else if (wy > -hx && invincibleTime == 0) // Right
                    enemy.onCollidedWithPlayer(Direction.RIGHT);
                else
                    enemy.onCollidedWithPlayer(Direction.DOWN);
            }
        }
    }

    @Override
    public void kill()
    {
        super.kill();
        y = 100;
        deaths++;
        lives--;
        new EffectDeath(x, y);
        invincibleTime = MAX_INVINCIBLE_TIME;

        if (lives > 0)
            Sounds.PLAYER_KILLED.start();
        else
        {
            Cubic.world.getMusicLoop().stop();
            Sounds.GAME_OVER.start();
        }
    }

    public void levelUp()
    {
        level++;
        lives++;

        new EffectLevelUp(x, y);

        for (int i = 0; i < level; i++)
            Cubic.addEnemy(Cubic.world.getEnemyList().createEnemy(EnemyType.NORMAL));

        if (level > 2 && random.nextInt(3) == 0)
            Cubic.addEnemy(Cubic.world.getEnemyList().createEnemy(EnemyType.RARE));
    }

    public void moveLeft()
    {
        xSpeed = Math.max(-1.75, xSpeed - 1.5);
    }

    public void moveRight()
    {
        xSpeed = Math.min(1.75, xSpeed + 1.5);
    }
}
