package wuxian.me.easyexecution.core.event;

import com.google.common.base.Preconditions;

/**
 * Created by wuxian on 9/12/2017.
 */


public class Event {
    private Object runner;
    private final EventType type;
    private final Object eventData;
    private final long time;

    private Event(final EventType type, final Object eventData) {
        this.type = type;
        this.eventData = eventData;
        this.time = System.currentTimeMillis();
    }

    public EventType getType() {
        return this.type;
    }

    public long getTime() {
        return this.time;
    }

    public Object getData() {
        return this.eventData;
    }

    public static Event create(final EventType type, final Object eventData)
            throws NullPointerException {
        //Preconditions.checkNotNull(eventData, "EventData was null");
        return new Event(type, eventData);
    }

}
