package juuxel.cubic.menu;

import juuxel.cubic.lib.GameValues;

import javax.swing.*;

public class CBasicLabel extends JLabel
{
    {
        setFont(GameValues.FONT);
    }

    public CBasicLabel(String text)
    {
        super(text);
    }

    public CBasicLabel(Icon icon)
    {
        super(icon);
    }
}
