/* This file is a part of the Cubic source code,
 * created by Juuxel.
 *
 * Cubic is licensed under the GNU LGPLv3 license.
 * Full source and license: https://github.com/Juuxel/Cubic
 */
package juuxel.cubic.event;

import java.util.concurrent.Flow;
import java.util.concurrent.SubmissionPublisher;

/**
 * The event bus distributes {@link Event events} between game components.
 */
public final class EventBus
{
    private static final SubmissionPublisher<Event> PUBLISHER = new SubmissionPublisher<>();

    private EventBus() throws Exception
    {
        throw new Exception("This class cannot be instantiated");
    }

    /**
     * Subscribes to the event type {@code E}, handling the events with the event handler.
     *
     * @param eventClass the event type class
     * @param eventHandler the event handler
     * @param <E> the event type
     *
     * @see EventHandler
     */
    public static <E extends Event> void subscribe(Class<E> eventClass, EventHandler<E> eventHandler)
    {
        PUBLISHER.subscribe(new EventSubscriber<>(eventClass, eventHandler));
    }

    /**
     * Posts a new event to the event bus and calls the handlers of the event type.
     *
     * @param event the event
     */
    public static void post(Event event)
    {
        PUBLISHER.submit(event);
    }

    private static final class EventSubscriber<E extends Event> implements Flow.Subscriber<Event>
    {
        private Flow.Subscription subscription;
        private final EventHandler<E> handler;
        private final Class<E> eventClass;

        private EventSubscriber(Class<E> eventClass, EventHandler<E> handler)
        {
            this.eventClass = eventClass;
            this.handler = handler;
        }

        @Override
        public void onSubscribe(Flow.Subscription subscription)
        {
            this.subscription = subscription;
            subscription.request(1);
        }

        @Override
        @SuppressWarnings("unchecked")
        public void onNext(Event item)
        {
            if (eventClass.isAssignableFrom(item.getClass()))
                handler.handle((E) item);

            subscription.request(1);
        }

        @Override
        public void onError(Throwable throwable)
        {

        }

        @Override
        public void onComplete()
        {

        }
    }
}
