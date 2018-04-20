/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.menu;

import juuxel.cubic.lib.GameValues;

import javax.swing.*;

public class CBasicLabel extends JLabel
{
    {
        setFont(GameValues.FONT);
    }

    public CBasicLabel(String text)
    {
        super(text);
    }

    public CBasicLabel(Icon icon)
    {
        super(icon);
    }
}
