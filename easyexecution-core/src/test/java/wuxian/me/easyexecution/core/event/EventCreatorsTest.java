package wuxian.me.easyexecution.core.event;

import org.junit.Test;

/**
 * Created by wuxian on 9/12/2017.
 */
public class EventCreatorsTest {

    @Test
    public void testJobCreate() {
        JobEventHandler handler = new JobEventHandler();
        EventCreators creators = new EventCreators();
        creators.addEventHandlers(handler);
        creators.fireEvent(Event.create(EventType.JOB_CREATED, null));
    }

    private class JobEventHandler implements EventHandler {
        @Override
        public void handleEvent(Event event) {
            if (event != null && event.getType() != null) {
                if (event.getType() == EventType.JOB_CREATED) {
                    System.out.println("job created");
                } else if (event.getType() == EventType.JOB_FINISHED) {
                    System.out.println("job finished");
                }
            }
        }
    }

}