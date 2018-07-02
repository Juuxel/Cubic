/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalScrollBarUI;
import java.awt.*;

public final class CScrollBarUI extends MetalScrollBarUI
{
    @SuppressWarnings("unused")
    public static ComponentUI createUI(JComponent c)
    {
        return new CScrollBarUI();
    }

    @Override
    protected void paintThumb(Graphics graphics, JComponent c, Rectangle thumbBounds)
    {
        var g = (Graphics2D) graphics;
        Color color = CButton.BACKGROUND;

        g.setColor(color);
        g.fill(thumbBounds);

        g.setColor(color.darker());
        g.setStroke(new BasicStroke(2));
        g.draw(thumbBounds);
    }
}
