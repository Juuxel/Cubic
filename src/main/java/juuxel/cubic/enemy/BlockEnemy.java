package juuxel.cubic.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.graphics.Graphics;
import juuxel.cubic.reference.Images;

public class BlockEnemy extends AbstractEnemy
{
    public BlockEnemy()
    {
        ignoreGround = true;
        slidable = false;
        x = Math.random() * Cubic.game.getWidth();
        y = 400;
        ySpeed = -0.5;
    }

    @Override
    public void move()
    {
        y += ySpeed;
    }

    @Override
    public void logic()
    {
        super.logic();

        if (Math.abs(x - Cubic.player.x) < 33 && y <= Cubic.player.y + 32)
            Cubic.player.kill();

        if (y < -20)
            Cubic.ENEMIES.remove(this);
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.BLOCK);
    }
}
