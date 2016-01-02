package juuxel.cubic.effect;

import juuxel.cubic.reference.Images;
import juuxel.cubic.graphics.Graphics;

import java.awt.*;

public final class LevelUpEffect extends Effect
{
    public static final Color[] EFFECT_COLORS = {
        Color.red,
        Color.orange,
        Color.yellow,
        Color.green,
        new Color(0f, 0.6f, 0.8f),
        new Color(0.7f, 0.3f, 0.7f)
    };

    public int lifetime = 0;

    public LevelUpEffect(double x, double y)
    {
        super(x, y);
    }

    @Override
    public void logic()
    {
        super.logic();

        ++lifetime;

        if (lifetime == 3)
        {
            new Particle(EFFECT_COLORS[random.nextInt(EFFECT_COLORS.length)], x, y);
            lifetime = 0;
        }
    }

    @Override
    public void draw(Graphics g)
    {
        drawCreature(g, Images.LEVEL_UP);
    }
}
