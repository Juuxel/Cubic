/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.level.Level;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

public final class LevelMenu extends CPanel
{
    public LevelMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        CPanel titlePanel = new CPanel();
        CPanel panel = new CPanel(new GridLayout(1, 0, 10, 0));

        titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, 40));
        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        panel.setOpaque(false);

        CLabel title = new CLabel("mainMenu.selectLevel");
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        title.setFont(GameValues.FONT.deriveFont(32F));
        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        Level.LEVELS.forEach(level -> {
            CButton button = new CButton(level.getNameKey());
            button.setFont(GameValues.FONT.deriveFont(24F));
            button.setBackground(new Color(0x7ceeebaa));

            button.addActionListener(e -> Cubic.newGame(level));

            panel.add(button);
        });

        titlePanel.add(backButton);
        titlePanel.add(Box.createHorizontalStrut(20));
        titlePanel.add(title);

        add(titlePanel);
        add(Box.createVerticalGlue());
        add(panel);
        add(Box.createVerticalGlue());
    }
}
