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
        setX(100);
        setY(100);
        setSpeedModifierY(2);
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
        setYSpeed(getYSpeed() - 0.1);

        if (onGround())
        {
            setYSpeed(0);
            setY(GameValues.GROUND);

            if (jumpPressed && !jumpWasPressed) setYSpeed(7);

            if (getXSpeed() != 0 && !Cubic.moveKeyDown) setXSpeed(getXSpeed() * 0.97);
        }

        if (getX() < -10)
            setX(Cubic.game.getWidth() + 10);
        if (getX() > Cubic.game.getWidth() + 10)
            setX(-10);

        jumpWasPressed = jumpPressed;
        jumpPressed = Cubic.jumpKeyDown;

        if (invincibleTime > 0)
            invincibleTime--;

        for (Enemy enemy : Cubic.ENEMIES)
        {
            // TODO Replace with Creature.onCollidedWith(Creature, Side)

            if (Math.abs(getX() - enemy.getX()) > 50 || Math.abs(getY() - enemy.getY()) > 33) return;

            if (enemy.getY() + 20 < getY()) enemy.kill();
            else if (invincibleTime == 0) kill();
        }
    }

    @Override
    public void kill()
    {
        super.kill();
        setY(100);
        Cubic.deaths++;
        Cubic.lives--;
        new EffectDeath(getX(), getY());
        invincibleTime = 200;
    }

    public void levelUp()
    {
        Cubic.level++;
        Cubic.lives++;

        new EffectLevelUp(getX(), getY());

        for (int i = 0; i < Cubic.level; i++)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        if (Cubic.level % 5 == 0)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.STRANGE));
    }
}
