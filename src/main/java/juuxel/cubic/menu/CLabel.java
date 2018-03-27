package juuxel.cubic.menu;

import juuxel.cubic.util.ILanguageChangeListener;
import juuxel.cubic.util.Translator;

import javax.swing.*;

public class CLabel extends CBasicLabel implements ILanguageChangeListener
{
    private String translationKey;
    private Object[] format = null;

    public CLabel(String translationKey)
    {
        super(Translator.translate(translationKey));

        this.translationKey = translationKey;
        Translator.addLanguageChangeListener(this);
    }

    public CLabel(String translationKey, Object... format)
    {
        super(Translator.format(translationKey, format));

        this.translationKey = translationKey;
        this.format = format;
        Translator.addLanguageChangeListener(this);
    }

    @Override
    public void onLanguageChange()
    {
        if (format == null)
            SwingUtilities.invokeLater(() -> setText(Translator.translate(translationKey)));
        else
            SwingUtilities.invokeLater(() -> setText(Translator.format(translationKey, format)));
    }
}
