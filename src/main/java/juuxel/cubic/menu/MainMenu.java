package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.Image;

public class MainMenu extends CPanel
{
    public MainMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));

        add(new CLabel(new ImageIcon(Images.LOGO.getScaledInstance(128, 64, Image.SCALE_FAST))));
        add(Box.createVerticalStrut(5));
        add(new CLabel(Translator.format("mainMenu.version", GameInfo.VERSION)));
        add(Box.createVerticalGlue());

        CButton playButton = new CButton(Translator.translate("mainMenu.play"));
        CButton optionsButton = new CButton(Translator.translate("mainMenu.options"));
        CButton quitButton = new CButton(Translator.translate("mainMenu.exit"));

        playButton.addActionListener(e -> Cubic.selectScreen("LevelMenu"));
        optionsButton.addActionListener(e -> Cubic.selectScreen("OptionsMenu"));
        quitButton.addActionListener(e -> System.exit(0));

        add(playButton);
        add(optionsButton);
        add(quitButton);
        add(Box.createVerticalGlue());
    }
}
