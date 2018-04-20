package juuxel.cubic.options;

import juuxel.cubic.util.Translator;

import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;

import static java.awt.event.KeyEvent.*;

public final class Options
{
    public static KeyBinding moveLeft;
    public static KeyBinding moveRight;
    public static KeyBinding jump;
    public static KeyBinding takeScreenshot;

    public static boolean captureFrame;
    public static int fps;

    public static final List<KeyBinding> KEY_BINDINGS = new ArrayList<>();

    private static Properties properties;
    private static KeyBinding currentlySelectedKeyBinding = null;

    private Options()
    {}

    public static void init()
    {
        moveLeft = addKeyBinding("controls.moveLeft", VK_A, false);
        moveRight = addKeyBinding("controls.moveRight", VK_D, false);
        jump = addKeyBinding("controls.jump", VK_SPACE, false);
        takeScreenshot = addKeyBinding("controls.takeScreenshot", VK_F12, false);
        captureFrame = false;
        fps = 60;

        properties = new Properties();
        properties.put("controls.moveLeft", moveLeft.toString());
        properties.put("controls.moveRight", moveRight.toString());
        properties.put("controls.jump", jump.toString());
        properties.put("controls.takeScreenshot", takeScreenshot.toString());
        properties.put("captureFrame", "false");
        properties.put("language", Translator.getLanguage());
        properties.put("fps", "60");

        try
        {
            Path path = Paths.get("options.properties");

            if (Files.notExists(path))
            {
                Files.createFile(path);
                writeOptions();
            }
            else
            {
                properties.load(Files.newBufferedReader(path));
                moveLeft.setValue(Integer.valueOf(properties.getProperty("controls.moveLeft")));
                moveRight.setValue(Integer.valueOf(properties.getProperty("controls.moveRight")));
                jump.setValue(Integer.valueOf(properties.getProperty("controls.jump")));
                takeScreenshot.setValue(Integer.valueOf(properties.getProperty("controls.takeScreenshot")));

                captureFrame = Boolean.parseBoolean(properties.getProperty("captureFrame"));
                fps = Integer.parseInt(properties.getProperty("fps"));

                Translator.setLanguage(properties.getProperty("language"));
                Translator.reloadStrings();
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static KeyBinding addKeyBinding(String name, int initialValue, boolean checkValue)
    {
        KeyBinding binding = new KeyBinding(name, initialValue);

        if (checkValue)
        {
            if (properties.containsKey(name))
                binding.setValue(Integer.parseInt(properties.getProperty(name)));
            else
                properties.setProperty(name, Integer.toString(initialValue));
        }

        KEY_BINDINGS.add(binding);

        return binding;
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
        properties.put("controls.moveLeft", moveLeft.toString());
        properties.put("controls.moveRight", moveRight.toString());
        properties.put("controls.jump", jump.toString());
        properties.put("controls.takeScreenshot", takeScreenshot.toString());
        properties.put("language", Translator.getLanguage());
        writeOptions();
    }

    public static void writeOptions()
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

    public static void selectKeyBinding(KeyBinding binding)
    {
        currentlySelectedKeyBinding = binding;
    }

    public static boolean isSelectingKeyBinding()
    {
        return currentlySelectedKeyBinding != null;
    }

    public static KeyBinding getCurrentKeyBinding()
    {
        return currentlySelectedKeyBinding;
    }
}
