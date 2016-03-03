package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.CreatureEffectDeath;
import juuxel.cubic.creature.fx.CreatureEffectLevelUp;
import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.sprite.Sprite;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.util.render.Graphics;

import java.io.IOException;

public final class CreaturePlayer extends Creature
{
    public int jumpsRemaining, invincibleTime = 0;
    public boolean jumpPressed, jumpWasPressed;

    public CreaturePlayer() throws IOException
    {
        setSprite(Images.player);
        x = 100;
        y = 100;
        xSpeed = 0;
        ySpeed = 0;
    }

    @Override
    public void draw(Graphics g)
    {
        if (invincibleTime % 2 == 0)
            drawCreature(g, getSprite());
    }

    @Override
    public void logic()
    {
        ySpeed -= 0.1;

        if (y <= GameValues.GROUND)
        {
            ySpeed = 0;
            y = GameValues.GROUND;
            jumpsRemaining = 2;

            if (jumpPressed && !jumpWasPressed && jumpsRemaining-- > 0) ySpeed = 7;

            if (xSpeed != 0 && !Cubic.moveKeyDown) xSpeed *= 0.97;
        }

        x += xSpeed;
        y += ySpeed / 2;

        if (x < -10)
            x = Cubic.game.getWidth() + 10;
        if (x > Cubic.game.getWidth() + 10)
            x = -10;

        jumpWasPressed = jumpPressed;
        jumpPressed = Cubic.jumpKeyDown;

        if (invincibleTime > 0)
            invincibleTime--;

        for (CreatureEnemy enemy : Cubic.ENEMIES)
        {
            if (Math.abs(x - enemy.x) > 50 || Math.abs(y - enemy.y) > 33) return;

            if (enemy.y + 20 < y) enemy.kill();
            else if (invincibleTime == 0) kill();
        }
    }

    @Override
    public void kill()
    {
        super.kill();
        y = 100;
        Cubic.deaths++;
        Cubic.lives--;
        new CreatureEffectDeath(x, y);
        invincibleTime = 200;
    }

    public void levelUp()
    {
        Cubic.level++;
        Cubic.lives++;

        new CreatureEffectLevelUp(x, y);

        for (int i = 0; i < Cubic.level; i++)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        if (Cubic.level % 5 == 0)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.STRANGE));
    }
}
