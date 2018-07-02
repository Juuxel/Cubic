/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.world.World;
import juuxel.cubic.lib.Images;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class WorldMenu extends CPanel
{
    public WorldMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        CPanel titlePanel = new CPanel();

        titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, 40));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));

        CLabel title = new CLabel("mainMenu.selectWorld");
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        title.setFont(CubicLookAndFeel.FONT.deriveFont(32F));
        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        titlePanel.add(backButton);
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(title);

        add(titlePanel);
        add(Box.createVerticalGlue());

        World.WORLDS.forEach(level -> {
            CButton button = new CButton(level.getNameKey());
            button.setFont(CubicLookAndFeel.FONT.deriveFont(24F));
            button.setBackground(new Color(0x7ceeebaa));
            button.setAlignmentX(Component.CENTER_ALIGNMENT);

            button.addActionListener(e -> Cubic.newGame(level));

            add(button);
            add(Box.createVerticalStrut(10));
        });

        add(Box.createVerticalGlue());
    }
}
