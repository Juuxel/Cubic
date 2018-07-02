/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.event.EventBus;
import juuxel.cubic.event.LanguageChangeEvent;
import juuxel.cubic.util.Translator;
import juuxel.cubic.util.Utils;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalButtonUI;
import java.awt.*;

public class CButton extends JButton
{
    static final Color BACKGROUND = CubicLookAndFeel.PRIMARY;
    private static final Color BACKGROUND_PRESSED = Utils.withAlpha(CubicLookAndFeel.ACCENT, 0.6f);
    private static final Color BACKGROUND_HOVER = CubicLookAndFeel.ACCENT;

    private String translationKey = "ui.button";

    public CButton(String translationKey, Color color)
    {
        super(Translator.translate(translationKey));

        this.translationKey = translationKey;

        setFont(CubicLookAndFeel.FONT);
        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new ButtonBorder());
        setForeground(color);
        EventBus.subscribe(LanguageChangeEvent.class, e -> onLanguageChange());
    }

    public CButton(String label)
    {
        this(label, CubicLookAndFeel.TEXT_COLOR);
    }

    public CButton(Icon icon)
    {
        super(icon);

        setContentAreaFilled(false);
        setFocusPainted(false);
        setBorder(new ButtonBorder());
    }

    protected void onLanguageChange()
    {
        SwingUtilities.invokeLater(() -> setText(Translator.translate(translationKey)));
    }

    @Override
    protected void paintComponent(Graphics graphics)
    {
        Graphics2D g = (Graphics2D) graphics.create();

        Color color = getBackgroundColor();
        g.setPaint(new GradientPaint(new Point(0, 0), color,
                                     new Point(0, getHeight()), color.darker()));
        g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
        g.fillRect(0, 0, getWidth(), getHeight());
        g.dispose();

        super.paintComponent(graphics);
    }

    private Color getBackgroundColor()
    {
        if (getModel().isPressed())
            return BACKGROUND_PRESSED;
        else if (getModel().isRollover())
            return BACKGROUND_HOVER;
        else
            return BACKGROUND;
    }

    public static class Basic extends CButton
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
            g.setColor(getBackgroundColor().darker());
            g.drawRect(x, y, width - 1, height - 1);
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

    /**
     * A {@code ComponentUI} for drawing any {@code AbstractButton} like a {@link CButton}.
     * This class is not used for {@code CButton}s, which already contain the drawing code.
     */
    public static final class UI extends MetalButtonUI
    {
        @SuppressWarnings("unused")
        public static ComponentUI createUI(JComponent c)
        {
            if (c instanceof CButton)
                return new MetalButtonUI();

            return new UI();
        }

        @Override
        public void update(Graphics graphics, JComponent c)
        {
            Graphics2D g = (Graphics2D) graphics.create();
            AbstractButton b = (AbstractButton) c;
            paintButton(g, b);
            paint(graphics, c);
        }

        @Override
        protected void paintButtonPressed(Graphics g, AbstractButton b)
        {
            paintButton((Graphics2D) g, b);
        }

        private void paintButton(Graphics2D g, AbstractButton b)
        {
            Color color = BACKGROUND;

            if (b.getModel().isPressed())
                color = BACKGROUND_PRESSED;
            else if (b.getModel().isRollover())
                color = BACKGROUND_HOVER;

            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.6f));
            g.setPaint(new GradientPaint(new Point(0, 0), color,
                                         new Point(0, b.getHeight()), color.darker()));
            g.fillRect(0, 0, b.getWidth(), b.getHeight());
            g.setComposite(AlphaComposite.SrcOver);
        }
    }
}
