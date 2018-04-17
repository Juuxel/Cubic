package juuxel.cubic.creature.enemy;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;

public class EnemyNormal extends Enemy
{
    public EnemyNormal()
    {
        x = Math.random() * Cubic.game.getWidth();
        y = GameValues.GROUND;
        setSprite(Images.enemy);
    }

    @Override
    public void move()
    {
        if (Cubic.player.getX() < x)
            xSpeed = Math.max(-1, xSpeed - 1);
        else if (Cubic.player.getX() > x)
            xSpeed = Math.min(1, xSpeed + 1);

        updateSpriteDirection();
    }

    @Override
    protected int getScoreBase()
    {
        return 50;
    }
}
