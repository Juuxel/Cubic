package juuxel.cubic.mod;

// TODO Document
/**
 * This is a mod for Cubic with extra metadata.
 */
public interface IModExtended extends IMod
{
    default String getId()
    {
        return getClass().getSimpleName();
    }

    default String getName()
    {
        return getId();
    }

    default String getAuthor()
    {
        return "";
    }

    default String getVersion()
    {
        return "1.0.0";
    }

    class ModImpl implements IModExtended
    {
        private final IMod mod;
        private final Mod annotation;

        public ModImpl(IMod mod, Mod annotation)
        {
            this.mod = mod;
            this.annotation = annotation;
        }

        @Override
        public String getId()
        {
            return annotation.id();
        }

        @Override
        public String getName()
        {
            return annotation.name();
        }

        @Override
        public String getAuthor()
        {
            return annotation.author();
        }

        @Override
        public String getVersion()
        {
            return annotation.version();
        }

        @Override
        public void coreInit()
        {
            mod.coreInit();
        }

        @Override
        public void contentInit()
        {
            mod.contentInit();
        }
    }
}
