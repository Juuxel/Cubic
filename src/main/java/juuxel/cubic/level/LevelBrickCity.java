package juuxel.cubic.level;

import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

public final class LevelBrickCity extends Level
{
    public LevelBrickCity()
    {
        super(Images.bricks);
    }

    @Override
    public String getNameKey()
    {
        return "level.brickCity";
    }
}
