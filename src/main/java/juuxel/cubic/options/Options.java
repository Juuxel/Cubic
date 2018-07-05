/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.options;

import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Sounds;
import juuxel.cubic.util.Translator;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

import static java.awt.event.KeyEvent.*;

public final class Options
{
    // Key bindings
    public static final Option<Integer> moveLeft       = new Option<>("controls.moveLeft", VK_A, true, Images.moveLeft);
    public static final Option<Integer> moveRight      = new Option<>("controls.moveRight", VK_D, true, Images.moveRight);
    public static final Option<Integer> jump           = new Option<>("controls.jump", VK_SPACE, true, Images.jump);
    public static final Option<Integer> takeScreenshot = new Option<>("controls.takeScreenshot", VK_F12, true,
                                                                      Images.camera);

    // Other options
    public static final Option<Boolean> captureFrame   = new Option<>("captureFrame", false);
    public static final Option<Integer> fps            = new Option<>("fps", 60);

    public static final Option<?>[] OPTIONS = { moveLeft, moveRight, jump, takeScreenshot, captureFrame, fps };

    private static Properties properties;
    private static Option<Integer> currentlySelectedKeyBinding = null;

    private Options()
    {}

    public static void init()
    {
        properties = new Properties();
        properties.put("controls.moveLeft", moveLeft.getValue().toString());
        properties.put("controls.moveRight", moveRight.getValue().toString());
        properties.put("controls.jump", jump.getValue().toString());
        properties.put("controls.takeScreenshot", takeScreenshot.getValue().toString());
        properties.put("captureFrame", captureFrame.getValue().toString());
        properties.put("language", Translator.getLanguage());
        properties.put("fps", fps.getValue().toString());
        properties.put("volume", Float.toString(Sounds.getVolume()));

        try
        {
            Path path = Paths.get("options.properties");

            if (Files.notExists(path))
            {
                Files.createFile(path);
            }
            else
            {
                properties.load(Files.newBufferedReader(path));
                moveLeft.setValue(Integer.valueOf(properties.getProperty("controls.moveLeft")));
                moveRight.setValue(Integer.valueOf(properties.getProperty("controls.moveRight")));
                jump.setValue(Integer.valueOf(properties.getProperty("controls.jump")));
                takeScreenshot.setValue(Integer.valueOf(properties.getProperty("controls.takeScreenshot")));

                captureFrame.setValue(Boolean.parseBoolean(properties.getProperty("captureFrame")));
                fps.setValue(Integer.valueOf(properties.getProperty("fps")));
                Sounds.setVolume(Float.parseFloat(properties.getProperty("volume")));

                Translator.setLanguage(properties.getProperty("language"));
                Translator.reloadStrings();
            }

            writeOptions();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static String getKeyName(int code)
    {
        switch (code)
        {
            case VK_SPACE:
                return Translator.translate("controls.keys.space");
            case VK_UP:
                return Translator.translate("controls.keys.up");
            case VK_LEFT:
                return Translator.translate("controls.keys.left");
            case VK_RIGHT:
                return Translator.translate("controls.keys.right");
            case VK_DOWN:
                return Translator.translate("controls.keys.down");
            default:
                return KeyEvent.getKeyText(code);
        }
    }

    public static void reloadAndWriteOptions()
    {
        properties.put("controls.moveLeft", moveLeft.getValue().toString());
        properties.put("controls.moveRight", moveRight.getValue().toString());
        properties.put("controls.jump", jump.getValue().toString());
        properties.put("controls.takeScreenshot", takeScreenshot.getValue().toString());
        properties.put("language", Translator.getLanguage());
        properties.put("volume", Float.toString(Sounds.getVolume()));
        writeOptions();
    }

    private static void writeOptions()
    {
        try (BufferedWriter writer = Files.newBufferedWriter(Paths.get("options.properties")))
        {
            properties.store(writer, "Cubic options");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void selectKeyBinding(Option<Integer> binding)
    {
        currentlySelectedKeyBinding = binding;
    }

    public static boolean isSelectingKeyBinding()
    {
        return currentlySelectedKeyBinding != null;
    }

    public static Option<Integer> getCurrentKeyBinding()
    {
        return currentlySelectedKeyBinding;
    }
}
