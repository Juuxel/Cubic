package juuxel.cubic.menu;

import juuxel.cubic.lib.GameValues;

import javax.swing.*;

public class CLabel extends JLabel
{
    {
        setFont(GameValues.FONT);
    }

    public CLabel(String label)
    {
        super(label);
    }

    public CLabel(Icon icon)
    {
        super(icon);
    }
}
