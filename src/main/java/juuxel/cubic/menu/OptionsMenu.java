/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.lib.Images;
import juuxel.cubic.options.*;
import juuxel.cubic.util.Sounds;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.*;
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

        CPanel panel = new CPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        CPanel languagePanel = new CPanel();
        CLabel languageTitle = new CLabel("options.language");
        JComboBox<String> comboBox = new JComboBox<>(Translator.getLanguageNames().toArray(new String[]{}));

        languageTitle.setAlignmentX(CENTER_ALIGNMENT);
        languageTitle.setFont(CubicLookAndFeel.FONT.deriveFont(24F));
        languageTitle.setIcon(new ImageIcon(Images.languageSelection));
        comboBox.setSelectedIndex(Translator.getLanguageIndex());
        comboBox.addActionListener(e -> {
            Translator.setLanguage(comboBox.getSelectedIndex());
            Translator.reloadStrings();
            Sounds.UI_CLICK.start();
        });
        comboBox.setFont(CubicLookAndFeel.FONT);

        languagePanel.add(comboBox);

        panel.add(languageTitle);
        panel.add(languagePanel);

        // TODO Add volume as a saved option
        var volumePanel = new CPanel();
        var volumeTitle = new CLabel("options.volume");
        volumeTitle.setAlignmentX(CENTER_ALIGNMENT);
        volumeTitle.setFont(CubicLookAndFeel.FONT.deriveFont(24F));
        volumeTitle.setIcon(new ImageIcon(Images.volume));

        JSlider slider = new JSlider(Math.max(-40, (int) Sounds.minVolume), (int) Sounds.maxVolume, 0);
        slider.setPaintLabels(false);
        slider.addChangeListener(e -> {
            float volume = slider.getValue();

            if (volume <= -40)
                volume = Sounds.minVolume;

            Sounds.setVolume(volume);
        });

        volumePanel.add(slider);
        panel.add(volumeTitle);
        panel.add(volumePanel);

        CPanel controlsPanel = new CPanel(new GridLayout(0, 2, 10, 5));
        CLabel controlsTitle = new CLabel("options.controls");
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        controlsTitle.setFont(CubicLookAndFeel.FONT.deriveFont(24F));
        controlsTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        controlsTitle.setIcon(new ImageIcon(Images.keyboard));

        panel.add(controlsTitle);
        panel.add(controlsPanel);

        for (Option<?> option : Options.OPTIONS)
        {
            if (option.isKey)
            {
                var binding = (Option<Integer>) option;
                var label = new CLabel(binding.getName());

                if (binding.getIcon() != null)
                    label.setIcon(new ImageIcon(binding.getIcon()));

                controlsPanel.add(label);
                controlsPanel.add(new KeyChooser(binding));
            }
        }

        add(new CMenuHeader("mainMenu.options", new ImageIcon(Images.optionsButton), e -> {
            choosers.forEach(c -> c.onChange(c.binding));
            Options.selectKeyBinding(null);
            Options.reloadAndWriteOptions();
        }), BorderLayout.NORTH);
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
