package juuxel.cubic.mod;

import juuxel.cubic.options.Options;
import juuxel.cubic.util.Strings;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public final class ModLoader
{
    private final List<IModExtended> mods;
    private final List<String> additionalMods;

    public ModLoader(List<String> additionalMods)
    {
        mods = new ArrayList<>();
        this.additionalMods = additionalMods;
    }

    public void init()
    {
        String modString = Options.mods;

        if ("".equals(modString)) return;

        List<String> modClassNames = new ArrayList<>(Arrays.asList(Strings.commaSplit(modString)));
        modClassNames.addAll(additionalMods);

        for (String name : modClassNames)
        {
            try
            {
                Class<?> modClass = Class.forName(name);

                IModExtended mod;

                if (implementsInterface(modClass, IModExtended.class))
                    mod = modClass.asSubclass(IModExtended.class).newInstance();
                else if (implementsInterface(modClass, IMod.class) && modClass.isAnnotationPresent(Mod.class))
                    mod = new IModExtended.ModImpl(modClass.asSubclass(IMod.class).newInstance(), modClass.getAnnotation(Mod.class));
                else
                {
                    System.err.printf("Class '%s' is not a valid mod class. %n", name);
                    continue;
                }

                System.out.printf("Initializing mod %d: %n", mods.size());
                System.out.printf("ID: %32s %n", mod.getId());
                System.out.printf("Name: %30s %n", mod.getName());
                System.out.printf("Author: %28s %n", mod.getAuthor());
                System.out.printf("Version: %27s %n", mod.getVersion());

                mods.add(mod);
            }
            catch (Throwable e)
            {
                System.err.printf("Error in initializing mod '%s'. %n", name);
                e.printStackTrace();
            }
        }
    }

    public void coreInit()
    {
        mods.forEach(mod -> {
            System.out.printf("Initializing core components for mod %s. %n", mod.getId());
            mod.coreInit();
        });
    }

    public void contentInit()
    {
        mods.forEach(mod -> {
            System.out.printf("Initializing contents for mod %s. %n", mod.getId());
            mod.contentInit();
        });
    }

    private static boolean implementsInterface(Class<?> c, Class<?> i)
    {
        for (Class<?> i2 : c.getInterfaces())
        {
            if (i2.equals(i))
                return true;
        }

        return false;
    }
}
