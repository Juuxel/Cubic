/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public final class MainMenu extends CPanel
{
    public static CButton continueButton;

    public MainMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(Box.createVerticalStrut(20));

        var logoLabel = new CBasicLabel(new ImageIcon(Images.LOGO.getScaledInstance(128, 64, Image.SCALE_FAST)));
        var versionLabel = new CLabel("mainMenu.version", GameInfo.VERSION);

        logoLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        versionLabel.setAlignmentX(Component.LEFT_ALIGNMENT);

        add(logoLabel);
        add(Box.createVerticalStrut(5));
        add(versionLabel);
        add(Box.createVerticalGlue());

        CButton playButton = new CButton("mainMenu.play");
        CButton optionsButton = new CButton("mainMenu.options");
        CButton quitButton = new CButton("mainMenu.exit");
        continueButton = new CButton("mainMenu.continue");
        CButton aboutButton = new CButton("mainMenu.about");
        CButton newsButton = new CButton("mainMenu.news");

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        aboutButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        newsButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(e -> Cubic.selectScreen("WorldMenu"));
        optionsButton.addActionListener(e -> Cubic.selectScreen("OptionsMenu"));
        aboutButton.addActionListener(e -> Cubic.selectScreen("AboutScreen"));
        quitButton.addActionListener(e -> System.exit(0));
        continueButton.addActionListener(e -> {
            Cubic.inStartScreen = false;
            Cubic.selectScreen("Game");
            continueButton.setVisible(false);
        });
        newsButton.addActionListener(e -> Cubic.selectScreen("NewsScreen"));

        playButton.setIcon(new ImageIcon(Images.playButton));
        aboutButton.setIcon(new ImageIcon(Images.aboutButton));
        optionsButton.setIcon(new ImageIcon(Images.optionsButton));
        quitButton.setIcon(new ImageIcon(Images.closeButton));
        newsButton.setIcon(new ImageIcon(Images.newsButton));
        continueButton.setVisible(false);

        var buttonPanel = new CPanel();
        buttonPanel.setLayout(new GridBagLayout());
        buttonPanel.setAlignmentX(Component.LEFT_ALIGNMENT);
        var constraints = new GridBagConstraints();

        constraints.insets = new Insets(7, 7, 7, 7);
        constraints.fill = GridBagConstraints.BOTH;

        constraints.gridwidth = 2;
        constraints.gridx = 0;
        constraints.gridy = 0;
        buttonPanel.add(continueButton, constraints);

        constraints.gridy = 1;
        buttonPanel.add(playButton, constraints);

        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.gridy = 2;
        buttonPanel.add(newsButton, constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        buttonPanel.add(optionsButton, constraints);

        constraints.gridx = 0;
        constraints.gridy = 3;
        buttonPanel.add(aboutButton, constraints);

        constraints.gridx = GridBagConstraints.RELATIVE;
        buttonPanel.add(quitButton, constraints);

        add(buttonPanel);
        add(Box.createVerticalGlue());
    }
}
