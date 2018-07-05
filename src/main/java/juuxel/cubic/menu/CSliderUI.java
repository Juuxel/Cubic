/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalSliderUI;
import java.awt.*;

public final class CSliderUI extends MetalSliderUI
{
    private CSliderUI() {}

    @SuppressWarnings("unused")
    public static ComponentUI createUI(JComponent c)
    {
        c.setOpaque(false);
        return new CSliderUI();
    }

    @Override
    public void paintThumb(Graphics g)
    {
        var thumb = thumbRect;
        var g2 = (Graphics2D) g;

        g2.setPaint(new GradientPaint(new Point(0, thumb.y), CubicLookAndFeel.PRIMARY,
                                      new Point(0, thumb.y + thumb.height), CubicLookAndFeel.PRIMARY.darker()));

        g2.fillRoundRect(thumb.x, thumb.y, thumb.width, thumb.height, 5, 5);
        g2.setPaint(CubicLookAndFeel.PRIMARY.darker());
        g2.drawRoundRect(thumb.x, thumb.y, thumb.width, thumb.height, 5, 5);
    }
}
