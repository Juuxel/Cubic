/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.Cubic;
import juuxel.cubic.lib.Images;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

public class NewsScreen extends CPanel
{
    public NewsScreen()
    {
        setLayout(new BorderLayout());

        CPanel titlePanel = new CPanel();
        CLabel title = new CLabel("news.title");
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        titlePanel.setMaximumSize(new Dimension(titlePanel.getMaximumSize().width, 40));
        title.setFont(CubicLookAndFeel.FONT.deriveFont(32F));
        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        titlePanel.add(backButton);
        titlePanel.add(Box.createHorizontalStrut(64));
        titlePanel.add(title);

        InputStream stream = this.getClass().getResourceAsStream("/data/text/changelog.txt");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));
        var textArea = new JTextArea(reader.lines().collect(Collectors.joining("\n")));

        textArea.setOpaque(false);
        textArea.setBackground(new Color(0, 0, 0, 0));
        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        add(titlePanel, BorderLayout.NORTH);
        add(textArea, BorderLayout.CENTER);
    }
}
