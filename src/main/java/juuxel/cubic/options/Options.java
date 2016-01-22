package juuxel.cubic.options;

import juuxel.cubic.util.Translator;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.*;
import java.util.Properties;

import static java.awt.event.KeyEvent.*;

public final class Options
{
    public static KeyBinding moveLeft;
    public static KeyBinding moveRight;
    public static KeyBinding jump;
    public static KeyBinding takeScreenshot;

    public static Font font;

    public static boolean captureFrame;

    private static Properties properties;

    private Options()
    {}

    public static void initialize() throws IOException, FontFormatException
    {
        moveLeft = new KeyBinding("controls.moveLeft", VK_A);
        moveRight = new KeyBinding("controls.moveRight", VK_D);
        jump = new KeyBinding("controls.jump", VK_SPACE);
        takeScreenshot = new KeyBinding("controls.takeScreenshot", VK_F2);
        captureFrame = false;
        font = null;

        properties = new Properties();
        properties.put("controls.moveLeft", moveLeft.toString());
        properties.put("controls.moveRight", moveRight.toString());
        properties.put("controls.jump", jump.toString());
        properties.put("controls.takeScreenshot", takeScreenshot.toString());
        properties.put("font", "default");
        properties.put("captureFrame", "false");
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
                moveLeft.setValue(Integer.valueOf(properties.getProperty("controls.moveLeft")));
                moveRight.setValue(Integer.valueOf(properties.getProperty("controls.moveRight")));
                jump.setValue(Integer.valueOf(properties.getProperty("controls.jump")));
                takeScreenshot.setValue(Integer.valueOf(properties.getProperty("controls.takeScreenshot")));

                captureFrame = Boolean.parseBoolean(properties.getProperty("captureFrame"));

                String fontValue = properties.getProperty("font");

                if (!fontValue.equals("default") && fontValue.contains(":"))
                {
                    String type = fontValue.substring(0, fontValue.indexOf(":"));
                    String fontName = fontValue.substring(fontValue.indexOf(":") + 1);

                    boolean file;

                    switch (type)
                    {
                        case "file":
                            file = true;
                            break;
                        case "system":
                            file = false;
                            break;
                        default:
                            throw new RuntimeException("Invalid font type: " + type);
                    }

                    font = file
                        ? Font.createFont(Font.TRUETYPE_FONT, Files.newInputStream(Paths.get(fontName))).deriveFont(16F)
                        : new Font(fontName, Font.PLAIN, 16);
                }

                Translator.setLanguage(properties.getProperty("language"));
                Translator.reloadProperties();
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
}
