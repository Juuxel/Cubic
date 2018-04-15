package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.options.Options;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.BorderLayout;

public final class OptionsMenu extends CPanel
{
    public OptionsMenu()
    {
        setLayout(new BorderLayout());

        CPanel titlePanel = new CPanel();
        CButton backButton = new CButton(new ImageIcon(Images.backButton));
        CLabel title = new CLabel("mainMenu.options");

        backButton.addActionListener(e -> {
            Cubic.selectScreen("MainMenu");
            Options.reloadAndWriteOptions();
        });
        title.setFont(GameValues.FONT.deriveFont(32F));

        titlePanel.add(backButton);
        titlePanel.add(title);

        CPanel panel = new CPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        CPanel languagePanel = new CPanel();
        JComboBox<String> comboBox = new JComboBox<>(Translator.getLanguageNames().toArray(new String[]{}));

        comboBox.setSelectedIndex(Translator.getLanguageIndex());

        comboBox.addActionListener(e -> {
            Translator.setLanguage(comboBox.getSelectedIndex());
            Translator.reloadStrings();
        });

        languagePanel.add(new CLabel("options.language"));
        languagePanel.add(comboBox);

        panel.add(languagePanel);

        add(titlePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);
    }
}
