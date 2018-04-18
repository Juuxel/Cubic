package juuxel.cubic.creature.fx;

import juuxel.cubic.lib.Images;

import java.awt.*;

public final class EffectLevelUp extends Effect
{
    public EffectLevelUp(double x, double y)
    {
        super(x, y + 64);
        setSprite(Images.levelUpEffect);
    }
}
