/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.event.EventBus;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.event.LanguageChangeEvent;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class CButton extends JButton
{
    private static final Color BACKGROUND = new Color(0x16b7fc);
    private static final Color BACKGROUND_PRESSED = BACKGROUND.darker();
    private static final Color BACKGROUND_HOVER = BACKGROUND.brighter();

    private final Color baseColor;
    private Color buttonColor;
    private String translationKey = "ui.button";

    public CButton(String translationKey, Color color)
    {
        super(Translator.translate(translationKey));

        baseColor = color;
        buttonColor = color;
        this.translationKey = translationKey;

        setFont(GameValues.FONT);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new ButtonBorder());
        setForeground(color);
        addMouseListener(new Mouse());
        EventBus.subscribe(LanguageChangeEvent.class, e -> onLanguageChange());
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

    protected void onLanguageChange()
    {
        SwingUtilities.invokeLater(() -> setText(Translator.translate(translationKey)));
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics.create();

        Color color = BACKGROUND;

        if (getModel().isPressed())
            color = BACKGROUND_PRESSED;
        else if (getModel().isRollover())
            color = BACKGROUND_HOVER;

        g.setPaint(new GradientPaint(new Point(0, 0), new Color(0, 0, 0, 0),
                                     new Point(0, getHeight()), color));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.5F));
        g.fillRoundRect(0, 0, getWidth(), getHeight(), 10, 10);
        g.dispose();

        super.paintComponent(graphics);
    }

    public static final class Basic extends CButton
    {
        public Basic(String label)
        {
            super(label);

            setText(label);
        }

        @Override
        public void onLanguageChange()
        {
        }
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
        }

        @Override
        public void mouseExited(MouseEvent e)
        {
            buttonColor = baseColor;
            setForeground(baseColor);
        }
    }
}
