/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.util.Sounds;

import javax.swing.*;
import javax.swing.plaf.ComponentUI;
import javax.swing.plaf.metal.MetalComboBoxUI;

public final class CComboBoxUI extends MetalComboBoxUI
{
    private final JComboBox<?> comboBox;

    private CComboBoxUI(JComboBox<?> comboBox)
    {
        this.comboBox = comboBox;
    }

    @SuppressWarnings("unused")
    public static ComponentUI createUI(JComponent c)
    {
        return new CComboBoxUI((JComboBox<?>) c);
    }

    @Override
    protected JButton createArrowButton()
    {
        var button = super.createArrowButton();

        button.addActionListener(e -> {
            if (comboBox.isPopupVisible())
                Sounds.UI_CLICK.start();
            else
                Sounds.UI_BACK.start();
        });

        return button;
    }
}
