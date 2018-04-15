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

/**
 * This class loads and initializes Cubic mods.
 *
 * <h2>Mod Format</h2>
 *
 * The Cubic mod jar format is simple:
 * <ul>
 *     <li>A mod class implementing {@link IMod} and optionally annotated by {@link ModMeta}.</li>
 *     <li>In the manifest file: {@code Mod-Class: your.mod.Class}</li>
 * </ul>
 */
public final class ModLoader
{
    private final static List<ModContainer> MODS = new ArrayList<>();
    private static boolean hasLoaded = false;

    private ModLoader()
    {}

    /**
     * Initializes the mod loader and loads mods from the mod directory.
     * All manually added mods should be added before calling this method.
     *
     * @see #detectJarMods()
     */
    public static void load()
    {
        if (!hasLoaded)
        {
            detectJarMods();

            MODS.forEach(mod -> {
                System.out.printf("Mod %d:%n", MODS.indexOf(mod));
                System.out.printf("    ID: %32s%n", mod.getId());
                System.out.printf("    Author: %28s%n", mod.getAuthor());
                System.out.printf("    Version: %27s%n", mod.getVersion());
            });
        }
        else
        {
            throw new IllegalStateException("The mod loader has already been loaded.");
        }
    }

    /**
     * Calls the {@code coreInit} phase of the mods.
     *
     * @see #contentInit()
     * @see IMod#coreInit()
     */
    public static void coreInit()
    {
        if (!hasLoaded)
            throw new IllegalStateException("The mod loader has not been loaded.");

        MODS.forEach(mod -> {
            System.out.printf("Initializing core components for mod %s.%n", mod.getId());
            mod.coreInit();
        });
    }

    /**
     * Calls the {@code contentInit} phase of the mods.
     *
     * @see #coreInit()
     * @see IMod#contentInit()
     */
    public static void contentInit()
    {
        if (!hasLoaded)
            throw new IllegalStateException("The mod loader has not been loaded.");

        MODS.forEach(mod -> {
            System.out.printf("Initializing contents for mod %s.%n", mod.getId());
            mod.contentInit();
        });
    }

    /**
     * Adds a mod class implementing {@link IMod} to the internal list.
     * Should be called before {@link #load()}.
     *
     * @param clazz the mod class
     */
    public static void addModClass(Class<? extends IMod> clazz)
    {
        if (!hasLoaded)
            MODS.add(createContainer(clazz));
    }

    /**
     * Looks for mods in the {@code ./mods} directory.
     */
    private static void detectJarMods()
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
                        MODS.add(container);
                }
            }
        }
        catch (IOException | ClassNotFoundException | RuntimeException e)
        {
            e.printStackTrace();
        }
    }

    private static URLClassLoader newURLLoader(Path path) throws IOException
    {
        return new URLClassLoader(new URL[] { path.toUri().toURL() }, ModLoader.class.getClassLoader());
    }

    private static ModContainer createContainer(Class<?> modClass)
    {
        try
        {
            if (implementsInterface(modClass, IMod.class))
            {
                ModContainer mod;

                Class<? extends IMod> iModClass = modClass.asSubclass(IMod.class);

                if (modClass.isAnnotationPresent(ModMeta.class))
                    mod = new ModContainer(iModClass.getDeclaredConstructor().newInstance(), modClass.getAnnotation(ModMeta.class));
                else
                    mod = new ModContainer(iModClass.getDeclaredConstructor().newInstance());

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

        ModContainer(IMod mod, ModMeta annotation)
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
