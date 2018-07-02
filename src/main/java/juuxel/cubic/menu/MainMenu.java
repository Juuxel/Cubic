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
import java.awt.Component;
import java.awt.Image;

public final class MainMenu extends CPanel
{
    public static CButton continueButton;

    public MainMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(Box.createVerticalStrut(20));

        add(new CBasicLabel(new ImageIcon(Images.LOGO.getScaledInstance(128, 64, Image.SCALE_FAST))));
        add(Box.createVerticalStrut(5));
        add(new CLabel("mainMenu.version", GameInfo.VERSION));
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

        add(continueButton);
        add(Box.createVerticalStrut(5));
        add(playButton);
        add(Box.createVerticalStrut(5));
        add(optionsButton);
        add(Box.createVerticalStrut(5));
        add(aboutButton);
        add(Box.createVerticalStrut(5));
        add(newsButton);
        add(Box.createVerticalStrut(5));
        add(quitButton);
        add(Box.createVerticalGlue());
    }
}
