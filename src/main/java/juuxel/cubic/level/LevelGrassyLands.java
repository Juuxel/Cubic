package juuxel.cubic.level;

import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

public class LevelGrassyLands extends Level
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
