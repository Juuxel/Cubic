/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.event.EventBus;
import juuxel.cubic.event.LanguageChangeEvent;
import juuxel.cubic.util.Translator;

import javax.swing.*;

public final class CLabel extends CBasicLabel
{
    private String translationKey;
    private Object[] format = null;

    public CLabel(String translationKey)
    {
        super(Translator.translate(translationKey));

        this.translationKey = translationKey;
        EventBus.subscribe(LanguageChangeEvent.class, e -> onLanguageChange());
    }

    public CLabel(String translationKey, Object... format)
    {
        super(Translator.format(translationKey, format));

        this.translationKey = translationKey;
        this.format = format;
        EventBus.subscribe(LanguageChangeEvent.class, e -> onLanguageChange());
    }

    private void onLanguageChange()
    {
        if (format == null)
            SwingUtilities.invokeLater(() -> setText(Translator.translate(translationKey)));
        else
            SwingUtilities.invokeLater(() -> setText(Translator.format(translationKey, format)));
    }
}
