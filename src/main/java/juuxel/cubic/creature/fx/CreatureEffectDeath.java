package juuxel.cubic.creature.fx;

import juuxel.cubic.lib.Images;

public final class CreatureEffectDeath extends CreatureEffect
{
    public CreatureEffectDeath(double x, double y)
    {
        super(x, y);
        spriteWidth = spriteHeight = 16;
        setSprite(Images.death);
    }
}
