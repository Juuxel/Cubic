package juuxel.cubic.level;

import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

public final class LevelGrassyLands extends Level
{
    public LevelGrassyLands()
    {
        super(Images.grass);
    }

    @Override
    public String getNameKey()
    {
        return "level.grassyLands";
    }
}
