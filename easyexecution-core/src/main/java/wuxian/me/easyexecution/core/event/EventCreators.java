package wuxian.me.easyexecution.core.event;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by wuxian on 9/12/2017.
 */
public class EventCreators {

    private final HashSet<EventHandler> listeners = new HashSet<>();

    public EventCreators() {
    }

    public void addEventHandlers(final EventHandler listener) {
        this.listeners.add(listener);
    }

    public void fireEvent(final Event event) {
        final ArrayList<EventHandler> listeners =
                new ArrayList<>(this.listeners);
        for (final EventHandler listener : listeners) {
            listener.handleEvent(event);
        }
    }

    public void removeEventHandlers(final EventHandler listener) {
        this.listeners.remove(listener);
    }
}