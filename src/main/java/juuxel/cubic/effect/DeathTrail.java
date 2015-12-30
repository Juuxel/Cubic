package juuxel.cubic.effect;

import juuxel.opengg.Graphics;

import java.awt.*;

public class DeathTrail extends Effect
{
    public int lifetime = 0;
    public final Image image;

    public DeathTrail(double x, double y, Image enemyImage)
    {
        super(x, y);
        image = enemyImage;
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, image);
    }

    @Override
    public void logic()
    {
        super.logic();

        ++lifetime;

        if (lifetime == 5)
        {
            lifetime = 0;
            new FallingEffect(new Color(255, 243, 194), x, y);
        }
    }
}
