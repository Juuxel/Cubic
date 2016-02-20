package juuxel.cubic.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.reference.GameValues;
import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;

public class NormalEnemy extends AbstractEnemy
{
    public NormalEnemy()
    {
        x = Math.random() * Cubic.game.getWidth();
        y = GameValues.GROUND;
    }

    public void draw(Graphics g)
    {
        drawCreature(g, Images.enemy);
    }

    @Override
    public void move()
    {
        if (Cubic.player.x < x)
            xSpeed = Math.max(-1, xSpeed - 1);
        else if (Cubic.player.x > x)
            xSpeed = Math.min(1, xSpeed + 1);
    }

    @Override
    protected int getScoreBase()
    {
        return 50;
    }
}
