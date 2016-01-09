package juuxel.cubic.options;

import juuxel.cubic.reference.Translator;

import java.awt.event.KeyEvent;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

import static java.awt.event.KeyEvent.*;

public final class Options
{
    public static KeyBinding moveLeft;
    public static KeyBinding moveRight;
    public static KeyBinding jump;

    private static Properties properties;

    private Options()
    {}

    public static void initialize()
    {
        moveLeft = new KeyBinding("controls.moveLeft", VK_A);
        moveRight = new KeyBinding("controls.moveRight", VK_D);
        jump = new KeyBinding("controls.jump", VK_SPACE);

        properties = new Properties();
        properties.put("controls.moveLeft", String.valueOf(VK_A));
        properties.put("controls.moveRight", String.valueOf(VK_D));
        properties.put("controls.jump", String.valueOf(VK_SPACE));
        properties.put("language", Translator.getLanguage());

        try
        {
            Path path = Paths.get("options.properties");

            if (!Files.exists(path))
            {
                Files.createFile(path);
                writeOptions();
            }
            else
            {
                properties.load(Files.newBufferedReader(path));
                Translator.setLanguage((String) properties.get("language"));
                moveLeft.setValue(Integer.valueOf((String) properties.get("controls.moveLeft")));
                moveRight.setValue(Integer.valueOf((String) properties.get("controls.moveRight")));
                jump.setValue(Integer.valueOf((String) properties.get("controls.jump")));
            }
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
            default:
                return KeyEvent.getKeyText(code);
        }
    }

    public static void reloadAndWriteOptions()
    {
        properties.put("controls.moveLeft", String.valueOf(moveLeft.getValue()));
        properties.put("controls.moveRight", String.valueOf(moveRight.getValue()));
        properties.put("controls.jump", String.valueOf(jump.getValue()));
        properties.put("language", Translator.getLanguage());
        writeOptions();
    }

    public static void writeOptions()
    {
        try
        {
            Path path = Paths.get("options.properties");
            properties.store(Files.newBufferedWriter(path), "Cubic options");
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
