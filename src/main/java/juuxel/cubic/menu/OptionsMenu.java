package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;

import javax.swing.*;

public class OptionsMenu extends CPanel
{
    public OptionsMenu()
    {
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        add(backButton);
    }
}
