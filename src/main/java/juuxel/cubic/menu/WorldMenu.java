/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.world.World;

import javax.swing.*;
import java.awt.*;

public final class WorldMenu extends CPanel
{
    public WorldMenu()
    {
        setLayout(new BorderLayout());
        var panel = new CPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        panel.add(Box.createVerticalGlue());

        World.WORLDS.forEach(level -> {
            CButton button = new CButton(level.getNameKey());
            button.setFont(CubicLookAndFeel.FONT.deriveFont(24F));
            button.setBackground(new Color(0x7ceeebaa));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            button.addActionListener(e -> Cubic.newGame(level));

            panel.add(button);
            panel.add(Box.createVerticalStrut(10));
        });

        panel.add(Box.createVerticalGlue());

        add(new CMenuHeader("mainMenu.selectWorld"), BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}
