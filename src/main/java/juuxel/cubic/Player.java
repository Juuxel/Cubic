package juuxel.cubic;

import juuxel.cubic.effect.DeathEffect;
import juuxel.cubic.effect.LevelUpEffect;
import juuxel.cubic.enemy.*;
import juuxel.cubic.reference.GameValues;
import juuxel.cubic.graphics.Graphics;

import java.awt.*;
import java.io.IOException;

public final class Player extends Creature
{
    public int jumpsRemaining, invincibleTime = 0;
    public boolean jumpPressed, jumpWasPressed;
    public final Image image;

    public Player(Image image) throws IOException
    {
        this.image = image;
        x = 100;
        y = 100;
        xSpeed = 0;
        ySpeed = 0;
    }

    @Override
    public void draw(Graphics g)
    {
        if (invincibleTime % 2 == 0)
            drawCreature(g, image);
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

        for (AbstractEnemy enemy : Cubic.ENEMIES)
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
        new DeathEffect(x, y);
        invincibleTime = 200;

        if (random.nextInt(7) == 1)
            GameValues.levelUp++;
    }

    public void levelUp()
    {
        Cubic.level++;
        Cubic.lives++;

        new LevelUpEffect(x, y);

        for (int i = 0; i < Cubic.level; i++)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.NORMAL));

        if (Cubic.level % 5 == 0)
            Cubic.ENEMIES.add(EnemyLists.createEnemy(EnemyType.STRANGE));
    }
}
