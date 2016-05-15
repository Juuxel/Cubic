package juuxel.cubic.creature.fx;

import juuxel.cubic.lib.Images;

import java.awt.*;

public final class EffectLevelUp extends Effect
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

    public EffectLevelUp(double x, double y)
    {
        super(x, y);
        setSprite(Images.levelUp);
    }

    @Override
    protected void logic()
    {
        super.logic();

        lifetime++;

        if (lifetime == 3)
        {
            new Particle(EFFECT_COLORS[random.nextInt(EFFECT_COLORS.length)], x, y);
            lifetime = 0;
        }
    }
}
