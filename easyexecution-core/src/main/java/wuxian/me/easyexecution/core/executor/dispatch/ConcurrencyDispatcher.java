package wuxian.me.easyexecution.core.executor.dispatch;

import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventCreators;
import wuxian.me.easyexecution.core.event.EventType;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;
import wuxian.me.easyexecution.core.util.ThreadPoolExecutingListener;
import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

/**
 * Created by wuxian on 10/12/2017.
 */
public class ConcurrencyDispatcher extends EventCreators implements IDispatcher, ThreadPoolExecutingListener {
    private JobRunnerManager jobRunnerManager;

    public ConcurrencyDispatcher(JobRunnerManager jobRunnerManager) {
        this.jobRunnerManager = jobRunnerManager;
    }

    @Override
    public void submit(Runnable runnable) {
        final Future<?> future = this.jobRunnerManager.getExecutorService().submit(runnable);
        this.fireEvent(Event.create(EventType.JOB_STARTED, runnable));
    }

    @Override
    public void cancelByExecId(String execId) {
        //do nothing
    }

    @Override
    public void beforeExecute(Runnable r) {

    }

    @Override
    public void afterExecute(Runnable r) {

    }
}
