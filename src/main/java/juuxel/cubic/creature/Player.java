package juuxel.cubic.creature;

import juuxel.cubic.Cubic;
import juuxel.cubic.creature.fx.EffectDeath;
import juuxel.cubic.creature.fx.EffectLevelUp;
import juuxel.cubic.creature.enemy.*;
import juuxel.cubic.lib.Images;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.render.Graphics;

import java.io.IOException;

public final class Player extends Creature
{
    public int invincibleTime = 0;
    public boolean jumpPressed, jumpWasPressed;

    public Player() throws IOException
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
        }

        if (x < -10)
            x = Cubic.game.getWidth() + 10;
        if (x > Cubic.game.getWidth() + 10)
            x = -10;

        jumpWasPressed = jumpPressed;
        jumpPressed = Cubic.jumpKeyDown;

        if (invincibleTime > 0)
            invincibleTime--;

        for (Enemy enemy : Cubic.ENEMIES)
        {
            // TODO Replace with Creature.onCollidedWith(Creature, Side)

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
}
