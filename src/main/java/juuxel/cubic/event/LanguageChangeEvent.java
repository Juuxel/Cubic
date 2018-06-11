/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.event;

/**
 * {@code LanguageChangeEvent} events are posted when the game language is changed.
 *
 * This event is a singleton: the only instance is {@link LanguageChangeEvent#INSTANCE}.
 */
public class LanguageChangeEvent implements Event
{
    /**
     * The only instance of this event.
     */
    public static final LanguageChangeEvent INSTANCE = new LanguageChangeEvent();

    private LanguageChangeEvent()
    {}
}
