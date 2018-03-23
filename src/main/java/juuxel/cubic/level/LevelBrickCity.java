package juuxel.cubic.level;

import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

public class LevelBrickCity extends Level
{
    public LevelBrickCity()
    {
        super(Images.bricks);
    }

    @Override
    public String getName()
    {
        return Translator.translate("level.brickCity");
    }
}
