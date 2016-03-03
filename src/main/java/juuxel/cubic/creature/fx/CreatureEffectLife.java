package juuxel.cubic.creature.fx;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;

public final class CreatureEffectLife extends CreatureEffect
{
    public CreatureEffectLife(double x, double y)
    {
        super(x, y);

        Cubic.lives++;
        setSprite(Images.life);
    }
}
