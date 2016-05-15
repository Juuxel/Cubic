package juuxel.cubic.creature.fx;

import juuxel.cubic.lib.Images;

public final class EffectDeath extends Effect
{
    public EffectDeath(double x, double y)
    {
        super(x, y);
        spriteWidth = spriteHeight = 16;
        setSprite(Images.death);
    }
}
