/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;

import javax.swing.*;
import java.awt.event.ActionListener;

final class CMenuHeader extends CPanel
{
    CMenuHeader(String title, Icon icon)
    {
        this(title, icon, null);
    }

    CMenuHeader(String title, Icon icon, ActionListener backButtonListener)
    {
        CLabel titleLabel = new CLabel(title);
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
        titleLabel.setFont(CubicLookAndFeel.FONT.deriveFont(32F));
        titleLabel.setIcon(icon);
        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        if (backButtonListener != null)
            backButton.addActionListener(backButtonListener);

        add(backButton);
        add(Box.createHorizontalStrut(10));
        add(titleLabel);
    }
}
