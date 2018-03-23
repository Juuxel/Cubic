package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameInfo;
import juuxel.cubic.lib.Images;
import juuxel.cubic.util.Translator;

import javax.swing.*;

public class MainMenu extends JPanel
{
    public MainMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        add(Box.createVerticalStrut(20));

        add(new JLabel(new ImageIcon(Images.LOGO)));
        add(new JLabel(Translator.format("mainMenu.version", GameInfo.VERSION)));
        add(Box.createVerticalGlue());

        JButton playButton = new JButton(Translator.translate("mainMenu.play"));
        JButton optionsButton = new JButton(Translator.translate("mainMenu.options"));
        JButton quitButton = new JButton(Translator.translate("mainMenu.exit"));

        playButton.addActionListener(e -> Cubic.selectScreen("LevelMenu"));
        optionsButton.addActionListener(e -> Cubic.selectScreen("OptionsMenu"));
        quitButton.addActionListener(e -> System.exit(0));

        add(playButton);
        add(optionsButton);
        add(quitButton);
        add(Box.createVerticalGlue());
    }
}
