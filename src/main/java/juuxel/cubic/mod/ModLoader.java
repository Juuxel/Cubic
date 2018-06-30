/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.mod;

import juuxel.cubic.util.Utils;

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
 *     <li>A mod class implementing {@link Mod} and optionally annotated by {@link Mod.Info}.</li>
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
                System.out.printf("| ID:      %s%n", mod.getId());
                System.out.printf("| Author:  %s%n", mod.getAuthor());
                System.out.printf("| Version: %s%n%n", mod.getVersion());
            });

            hasLoaded = true;
        }
        else
        {
            throw new IllegalStateException("The mod loader has already been loaded.");
        }
    }

    /**
     * Initializes the mods.
     *
     * @see Mod#init()
     */
    public static void init()
    {
        if (!hasLoaded)
            throw new IllegalStateException("The mod loader has not been loaded.");

        MODS.forEach(mod -> {
            System.out.printf("Initializing mod %s.%n", mod.getId());
            mod.init();
        });
    }

    /**
     * Adds a mod class implementing {@link Mod} to the internal list.
     * Should be called before {@link #load()}.
     *
     * @param clazz the mod class
     */
    @SuppressWarnings("unused")
    public static void addModClass(Class<? extends Mod> clazz)
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
            if (Utils.implementsInterface(modClass, Mod.class))
            {
                ModContainer mod;

                Class<? extends Mod> iModClass = modClass.asSubclass(Mod.class);

                if (modClass.isAnnotationPresent(Mod.Info.class))
                    mod = new ModContainer(iModClass.getDeclaredConstructor().newInstance(), modClass.getAnnotation(Mod.Info.class));
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

    private static final class ModContainer
    {
        private final String id, version, author;
        private final Mod mod;

        ModContainer(Mod mod)
        {
            this.mod = mod;
            id = mod.getClass().getName();
            version = "";
            author = "";
        }

        ModContainer(Mod mod, Mod.Info annotation)
        {
            this.mod = mod;
            id = annotation.id();
            version = annotation.version();
            author = annotation.author();
        }

        void init()
        { mod.init(); }

        String getId()
        { return id; }

        String getVersion()
        { return version; }

        String getAuthor()
        { return author; }
    }
}
