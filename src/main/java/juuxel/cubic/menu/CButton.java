package juuxel.cubic.menu;

import juuxel.cubic.lib.GameValues;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CButton extends JButton
{
    private final Color baseColor;
    private Color buttonColor;

    public CButton(String label, Color color)
    {
        super(label);

        baseColor = color;
        buttonColor = color;

        setFont(GameValues.FONT);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new ButtonBorder());
        setForeground(color);
        addMouseListener(new Mouse());
    }

    public CButton(String label)
    {
        this(label, Color.BLACK);
    }

    public CButton(Icon icon)
    {
        super(icon);

        baseColor = Color.BLACK;
        buttonColor = Color.BLACK;

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new ButtonBorder());
        addMouseListener(new Mouse());
    }

    private class ButtonBorder implements Border
    {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height)
        {
            g.setColor(buttonColor);
            g.drawRoundRect(x, y, width - 1, height - 1, 5, 5);
        }

        @Override
        public Insets getBorderInsets(Component c)
        {
            return new Insets(6, 6, 6, 6);
        }

        @Override
        public boolean isBorderOpaque()
        {
            return true;
        }
    }

    private class Mouse extends MouseAdapter
    {
        @Override
        public void mouseEntered(MouseEvent e)
        {
            buttonColor = baseColor.brighter();
            setForeground(baseColor.brighter());
            System.out.println("HEHE");
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            buttonColor = baseColor;
            setForeground(baseColor);
            System.out.println(":(");
        }
    }
}
