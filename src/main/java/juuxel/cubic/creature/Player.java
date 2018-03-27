package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.EffectDeath;
import juuxel.cubic.creature.fx.EffectLevelUp;
import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.lib.Images;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.render.Graphics;
import juuxel.cubic.util.Direction;

import java.io.IOException;

public final class Player extends Creature
{
    public int invincibleTime = 0;
    public boolean jumpPressed, jumpWasPressed;

    public Player()
    {
        setCollisionEnabled(true);
        setFlippingEnabled(true);
        setSprite(Images.player);
        x = 100;
        y = 100;
        speedModifierY = 2;
    }

    @Override
    public void draw(Graphics g)
    {
        if (invincibleTime % 2 == 0)
            drawCreature(g, getSprite());
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

        if ((x < -10 && xSpeed < 0) || (x > Cubic.game.getWidth() && xSpeed > 0))
            xSpeed = -xSpeed;

        jumpWasPressed = jumpPressed;
        jumpPressed = Cubic.jumpKeyDown;

        if (invincibleTime > 0)
            invincibleTime--;

        for (Enemy enemy : Cubic.ENEMIES)
        {
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
        Cubic.deaths++;
        Cubic.lives--;
        new EffectDeath(x, y);
        invincibleTime = 200;
    }

    public void levelUp()
    {
        Cubic.level++;
        Cubic.lives++;

        new EffectLevelUp(x, y);

        for (int i = 0; i < Cubic.level; i++)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        if (Cubic.level % 5 == 0)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.STRANGE));
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
