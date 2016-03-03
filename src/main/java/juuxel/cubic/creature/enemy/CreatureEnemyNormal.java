package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.render.Graphics;

public class CreatureEnemyNormal extends CreatureEnemy
{
    public CreatureEnemyNormal()
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
