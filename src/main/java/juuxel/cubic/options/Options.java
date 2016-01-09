package juuxel.cubic.options;

import juuxel.cubic.reference.Translator;

import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;

public final class Options
{
    public static KeyBinding moveLeft;
    public static KeyBinding moveRight;
    public static KeyBinding jump;

    private Options()
    {}

    public static void initialize()
    {
        moveLeft = new KeyBinding("controls.moveLeft", VK_A);
        moveRight = new KeyBinding("controls.moveRight", VK_D);
        jump = new KeyBinding("controls.jump", VK_SPACE);
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
}
