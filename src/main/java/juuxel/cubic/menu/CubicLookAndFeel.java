/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.util.Utils;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.plaf.metal.MetalLookAndFeel;
import javax.swing.plaf.metal.OceanTheme;
import java.awt.Color;

public final class CubicLookAndFeel extends MetalLookAndFeel
{
    static final Color PRIMARY = new Color(0xFFFFFF);
    static final Color ACCENT = new Color(0x16b7fc);

    public static void init()
    {
        MetalLookAndFeel.setCurrentTheme(new Theme());

        try
        {
            UIManager.setLookAndFeel(new CubicLookAndFeel());
        }
        catch (UnsupportedLookAndFeelException e)
        {
            System.err.println("Exception thrown while setting the look and feel.");
            e.printStackTrace();
        }
    }

    @Override
    protected void initClassDefaults(UIDefaults table)
    {
        super.initClassDefaults(table);

        Object[] defaults = {
                "ButtonUI", CButton.UI.class.getName(),
                "ScrollBarUI", CScrollBarUI.class.getName()
        };

        table.putDefaults(defaults);
    }

    private static class Theme extends OceanTheme
    {
        private static final ColorUIResource PRIMARY_RESOURCE = new ColorUIResource(PRIMARY);
        private static final ColorUIResource PRIMARY_DARK_RESOURCE = new ColorUIResource(PRIMARY.darker());
        private static final ColorUIResource ACCENT_RESOURCE = new ColorUIResource(Utils.withAlpha(ACCENT, 0.6f));

        @Override
        protected ColorUIResource getPrimary1()
        {
            return PRIMARY_RESOURCE;
        }

        @Override
        protected ColorUIResource getPrimary2()
        {
            return ACCENT_RESOURCE;
        }

        @Override
        protected ColorUIResource getPrimary3()
        {
            return PRIMARY_DARK_RESOURCE;
        }

        @Override
        protected ColorUIResource getSecondary1()
        {
            return PRIMARY_DARK_RESOURCE;
        }

        @Override
        protected ColorUIResource getSecondary2()
        {
            return PRIMARY_DARK_RESOURCE;
        }

        @Override
        protected ColorUIResource getSecondary3()
        {
            return PRIMARY_RESOURCE;
        }

        @Override
        public String getName()
        {
            return "Cubic";
        }
    }
}
