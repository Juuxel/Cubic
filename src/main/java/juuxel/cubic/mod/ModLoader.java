package juuxel.cubic.mod;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

public final class ModLoader
{
    private final List<ModContainer> mods;

    public ModLoader()
    {
        mods = new ArrayList<>();
    }

    public void init()
    {
        detectJarMods();

        mods.forEach(mod -> {
            System.out.printf("Mod %d:%n", mods.indexOf(mod));
            System.out.printf("    ID: %32s%n", mod.getId());
            System.out.printf("    Author: %28s%n", mod.getAuthor());
            System.out.printf("    Version: %27s%n", mod.getVersion());
        });
    }

    public void coreInit()
    {
        mods.forEach(mod -> {
            System.out.printf("Initializing core components for mod %s.%n", mod.getId());
            mod.coreInit();
        });
    }

    public void contentInit()
    {
        mods.forEach(mod -> {
            System.out.printf("Initializing contents for mod %s.%n", mod.getId());
            mod.contentInit();
        });
    }

    private void detectJarMods()
    {
        try
        {
            Path modDir = Paths.get("mods");

            if (Files.notExists(modDir))
            {
                Files.createDirectories(modDir);

                return;
            }

            DirectoryStream<Path> stream = Files.newDirectoryStream(modDir);

            for (Path p : stream)
            {
                if (p.toString().endsWith(".jar"))
                {
                    System.out.printf("Reading jar '%s' for mods.%n", p.getFileName());

                    URLClassLoader jarLoader = newURLLoader(p);

                    URL url = jarLoader.findResource("META-INF/MANIFEST.MF");
                    Manifest manifest = new Manifest(url.openStream());
                    Attributes a = manifest.getMainAttributes();
                    String modClass = a.getValue("Mod-Class");

                    ModContainer container = createContainer(Class.forName(modClass, true, jarLoader));

                    if (container != null)
                        mods.add(container);
                }
            }
        }
        catch (IOException | ClassNotFoundException | RuntimeException e)
        {
            e.printStackTrace();
        }
    }

    private URLClassLoader newURLLoader(Path path) throws IOException
    {
        return new URLClassLoader(new URL[] { path.toUri().toURL() }, getClass().getClassLoader());
    }

    private ModContainer createContainer(Class<?> modClass)
    {
        try
        {
            if (implementsInterface(modClass, IMod.class))
            {
                ModContainer mod;

                Class<? extends IMod> iModClass = modClass.asSubclass(IMod.class);

                if (modClass.isAnnotationPresent(Mod.class))
                    mod = new ModContainer(iModClass.newInstance(), modClass.getAnnotation(Mod.class));
                else
                    mod = new ModContainer(iModClass.newInstance());

                return mod;
            }
            else
            {
                System.err.printf("Class '%s' is not a valid mod class.%n", modClass.getName());
            }
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return null;
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

    public static class ModContainer
    {
        private final String id, version, author;
        private final IMod mod;

        ModContainer(IMod mod)
        {
            this.mod = mod;
            id = mod.getClass().getName();
            version = "";
            author = "";
        }

        ModContainer(IMod mod, Mod annotation)
        {
            this.mod = mod;
            id = annotation.id();
            version = annotation.version();
            author = annotation.author();
        }

        void coreInit()
        { mod.coreInit(); }

        void contentInit()
        { mod.contentInit(); }

        public String getId()
        { return id; }

        public String getVersion()
        { return version; }

        public String getAuthor()
        { return author; }
    }
}
