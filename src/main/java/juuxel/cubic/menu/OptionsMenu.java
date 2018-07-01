/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.lib.Images;
import juuxel.cubic.options.*;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

public final class OptionsMenu extends CPanel
{
    private final List<KeyChooser> choosers = new ArrayList<>();

    @SuppressWarnings("unchecked")
    public OptionsMenu()
    {
        setLayout(new BorderLayout());

        CPanel titlePanel = new CPanel();
        CButton backButton = new CButton(new ImageIcon(Images.backButton));
        CLabel title = new CLabel("mainMenu.options");

        backButton.addActionListener(e -> {
            Cubic.selectScreen("MainMenu");
            choosers.forEach(c -> c.onChange(c.binding));
            Options.selectKeyBinding(null);
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

        CPanel controlsPanel = new CPanel(new GridLayout(0, 2, 10, 5));
        CLabel controlsTitle = new CLabel("options.controls");
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        controlsTitle.setFont(GameValues.FONT.deriveFont(24F));
        controlsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(controlsTitle);
        panel.add(controlsPanel);

        for (Option<?> option : Options.OPTIONS)
        {
            if (option.isKey)
            {
                var binding = (Option<Integer>) option;

                controlsPanel.add(new CLabel(binding.getName()));
                controlsPanel.add(new KeyChooser(binding));
            }
        }

        add(titlePanel, BorderLayout.NORTH);
        add(panel, BorderLayout.CENTER);

        addKeyListener(new Keyboard());
    }

    private static final class Keyboard extends KeyAdapter
    {
        @Override
        public void keyPressed(KeyEvent e)
        {
            if (Options.isSelectingKeyBinding())
            {
                Options.getCurrentKeyBinding().setValue(e.getKeyCode());
                Options.selectKeyBinding(null);
            }
        }
    }

    private final class KeyChooser extends CButton.Basic implements ActionListener, Option.ChangeListener<Integer>
    {
        private final Option<Integer> binding;

        private KeyChooser(Option<Integer> binding)
        {
            super(Options.getKeyName(binding.getValue()));

            addActionListener(this);

            this.binding = binding;
            binding.addChangeListener(this);

            choosers.add(this);
        }

        @Override
        public void actionPerformed(ActionEvent e)
        {
            Options.selectKeyBinding(binding);
            setText(Translator.translate("controls.selecting"));
            OptionsMenu.this.requestFocusInWindow();
        }

        @Override
        public void onChange(Option<Integer> option)
        {
            if (option.equals(Options.getCurrentKeyBinding()))
                setText(Options.getKeyName(option.getValue()));
        }
    }
}
