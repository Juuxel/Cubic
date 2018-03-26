package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.Color;
import java.awt.Component;
import java.awt.Image;

public class MainMenu extends CPanel
{
    public static CButton continueButton;

    public MainMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(new EmptyBorder(10, 10, 10, 10));

        add(Box.createVerticalStrut(20));

        add(new CLabel(new ImageIcon(Images.LOGO.getScaledInstance(128, 64, Image.SCALE_FAST))));
        add(Box.createVerticalStrut(5));
        add(new CLabel(Translator.format("mainMenu.version", GameInfo.VERSION)));
        add(Box.createVerticalGlue());

        CButton playButton = new CButton(Translator.translate("mainMenu.play"));
        CButton optionsButton = new CButton(Translator.translate("mainMenu.options"));
        CButton quitButton = new CButton(Translator.translate("mainMenu.exit"));
        continueButton = new CButton(Translator.translate("mainMenu.continue"));

        playButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        optionsButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        quitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        continueButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        playButton.addActionListener(e -> Cubic.selectScreen("LevelMenu"));
        optionsButton.addActionListener(e -> Cubic.selectScreen("OptionsMenu"));
        quitButton.addActionListener(e -> System.exit(0));
        continueButton.addActionListener(e -> {
            Cubic.inStartScreen = false;
            Cubic.selectScreen("Game");
            continueButton.setVisible(false);
        });

        continueButton.setVisible(false);

        add(continueButton);
        add(Box.createVerticalStrut(5));
        add(playButton);
        add(Box.createVerticalStrut(5));
        add(optionsButton);
        add(Box.createVerticalStrut(5));
        add(quitButton);
        add(Box.createVerticalGlue());
    }
}
