/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.event;

/**
 * Event handlers handle events.
 *
 * @param <E> the event type accepted by this {@code EventHandler}
 * @see EventBus
 */
@FunctionalInterface
public interface EventHandler<E extends Event>
{
    /**
     * Handles the event.
     *
     * @param event the event
     */
    void handle(E event);
}
