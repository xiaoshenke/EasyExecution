package wuxian.me.easyexecution.core.executor.dispatch;

import com.google.common.collect.Lists;
import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventCreators;
import wuxian.me.easyexecution.core.event.EventType;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;
import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.List;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuxian on 10/12/2017.
 */
public class FixedFrequencyDispatcher extends EventCreators implements IDispatcher, Runnable {

    private Thread dispatchThread;
    private Object lock = new Object();
    private List<Runnable> queue = Lists.newArrayList();
    private JobRunnerManager jobRunnerManager;

    private long rate;   //millis

    public FixedFrequencyDispatcher(JobRunnerManager jobRunnerManager, long rate) {
        this.jobRunnerManager = jobRunnerManager;
        this.rate = rate;
        dispatchThread = new Thread(this);
        dispatchThread.setName("FixedFrequencyDispatcherThread");
        dispatchThread.start();
    }

    @Override
    public void submit(Runnable runnable) {
        synchronized (lock) {
            queue.add(runnable);
        }
    }

    @Override
    public void cancelByExecId(String execId) {
        //TODO
    }

    @Override
    public void run() {
        while (true) {
            if (queue.size() != 0) {
                synchronized (lock) {
                    if (queue.size() != 0) {
                        Runnable runnable = queue.get(0);
                        queue.remove(0);
                        this.jobRunnerManager.getExecutorService().submit(runnable);
                        fireEvent(Event.create(EventType.JOB_STARTED, runnable));
                    }
                }
            }

            try {
                Thread.sleep(rate);
            } catch (InterruptedException e) {
                ;
            }
        }

    }
}
