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
import juuxel.cubic.util.Translator;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public final class AboutScreen extends CPanel
{
    public AboutScreen()
    {
        setLayout(new BorderLayout());

        CPanel titlePanel = new CPanel();
        CLabel title = new CLabel("about.title");
        CButton backButton = new CButton(new ImageIcon(Images.backButton));

        titlePanel.setLayout(new BoxLayout(titlePanel, BoxLayout.X_AXIS));
        title.setFont(GameValues.FONT.deriveFont(32F));
        backButton.addActionListener(e -> Cubic.selectScreen("MainMenu"));

        titlePanel.add(backButton);
        titlePanel.add(Box.createHorizontalStrut(64));
        titlePanel.add(title);

        JTextPane textArea = new JTextPane();

        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());

        StyledDocument doc = textArea.getStyledDocument();

        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 20);

        try
        {
            doc.insertString(0, "Copyright \u00a92016-2018 Juuxel\n\n", attributes);
            doc.insertString(0, Translator.translate("about.text") + "\n\n", attributes);
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }

        InputStream stream = this.getClass().getResourceAsStream("/data/text/LICENSE");
        BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

        reader.lines().forEach(s -> {
            try
            {
                doc.insertString(doc.getLength(), s + '\n', null);
            }
            catch (BadLocationException e)
            {
                e.printStackTrace();
            }
        });

        var scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(CubicLookAndFeel.PRIMARY.darker())));
        scrollPane.setOpaque(false);

        add(titlePanel, BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
