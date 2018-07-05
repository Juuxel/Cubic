/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

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

        JTextPane textArea = new JTextPane();

        textArea.setEditable(false);
        textArea.setBorder(BorderFactory.createEmptyBorder());

        StyledDocument doc = textArea.getStyledDocument();

        SimpleAttributeSet attributes = new SimpleAttributeSet();
        StyleConstants.setBold(attributes, true);
        StyleConstants.setFontSize(attributes, 20);

        try
        {
            doc.insertString(0, "Cubic includes the Sansus Webissimo font by Sergiy S. Tkachenko, " +
                    "licensed under the CC-BY 3.0 license. " +
                    "See: https://creativecommons.org/licenses/by/3.0/\n\n", null);
            doc.insertString(0, "Copyright \u00a92016-2018 Juuxel\n\n", attributes);
            doc.insertString(0, Translator.translate("about.text") + "\n\n", attributes);
            doc.insertString(doc.getLength(), "Sounds\n", attributes);

            InputStream soundStream = this.getClass().getResourceAsStream("/data/text/sounds.txt");
            BufferedReader soundReader = new BufferedReader(new InputStreamReader(soundStream));

            soundReader.lines().forEach(s -> {
                try
                {
                    doc.insertString(doc.getLength(), s + '\n', null);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
            });

            InputStream licenseStream = this.getClass().getResourceAsStream("/data/text/LICENSE");
            BufferedReader licenseReader = new BufferedReader(new InputStreamReader(licenseStream));

            doc.insertString(doc.getLength(), "\nLicense\n", attributes);

            licenseReader.lines().forEach(s -> {
                try
                {
                    doc.insertString(doc.getLength(), s + '\n', null);
                }
                catch (BadLocationException e)
                {
                    e.printStackTrace();
                }
            });
        }
        catch (BadLocationException e)
        {
            e.printStackTrace();
        }

        var scrollPane = new JScrollPane(textArea);
        scrollPane.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createEmptyBorder(20, 20, 20, 20),
                BorderFactory.createLineBorder(CubicLookAndFeel.PRIMARY.darker())));
        scrollPane.setOpaque(false);

        add(new CMenuHeader("about.title", new ImageIcon(Images.aboutButton)), BorderLayout.NORTH);
        add(scrollPane, BorderLayout.CENTER);
    }
}
