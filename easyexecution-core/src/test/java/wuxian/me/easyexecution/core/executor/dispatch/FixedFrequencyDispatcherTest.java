package wuxian.me.easyexecution.core.executor.dispatch;

import org.junit.Test;
import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventHandler;
import wuxian.me.easyexecution.core.event.EventType;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 10/12/2017.
 */
public class FixedFrequencyDispatcherTest {

    @Test
    public void testRunJob() {

        JobRunnerManager manager = new JobRunnerManager();
        FixedFrequencyDispatcher dispatcher =
                new FixedFrequencyDispatcher(manager, 1000);
        dispatcher.addEventHandlers(new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                if (event != null) {
                    if (event.getType() == EventType.JOB_STARTED) {
                        System.out.println("Event JOB_STARTED received,EventData is "
                                + event.getData().toString());
                    }
                }
            }
        });

        EchoRunnable runnable1 = new EchoRunnable("1");
        EchoRunnable runnable2 = new EchoRunnable("2");
        dispatcher.submit(runnable1);
        dispatcher.submit(runnable2);

        while (true) {

        }
    }

    private class EchoRunnable implements Runnable {

        private String msg;

        public EchoRunnable(String msg) {
            this.msg = msg;
        }

        @Override
        public void run() {
            System.out.println("echo runnable.run,message: " + msg);
            System.out.println("current time: " + System.currentTimeMillis());
        }
    }

}