package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.level.Level;
import juuxel.cubic.lib.GameValues;
import juuxel.cubic.util.Translator;

import javax.swing.*;
import java.awt.Font;
import java.awt.GridLayout;

public class LevelMenu extends CPanel
{
    public LevelMenu()
    {
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        CPanel panel = new CPanel(new GridLayout(1, 0, 10, 0));
        panel.setOpaque(false);

        Level.LEVELS.forEach(level -> {
            CButton button = new CButton(level.getName());
            button.setFont(GameValues.FONT.deriveFont(24F));

            button.addActionListener(e -> {
                Cubic.gameLevel = level;
                Cubic.inStartScreen = false;
                Cubic.selectScreen("Game");
                Cubic.GamePane pane = Cubic.game.getGamePane();
                pane.requestFocusInWindow();
            });

            panel.add(button);
        });

        add(Box.createVerticalStrut(20));
        add(new CLabel(Translator.translate("mainMenu.selectLevel")));

        add(Box.createVerticalGlue());
        add(panel);
        add(Box.createVerticalGlue());
    }
}
