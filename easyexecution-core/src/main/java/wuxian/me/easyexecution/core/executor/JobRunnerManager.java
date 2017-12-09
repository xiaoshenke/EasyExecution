package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.util.ThreadPoolExecutingListener;
import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JobRunnerManager implements ThreadPoolExecutingListener {

    private static final int DEFAULT_FLOW_NUM_JOB_TREADS = 10;

    private TrackingThreadPool executorService;

    private TrackingThreadPool createExecutorService(final int nThreads) {
        executorService =  new TrackingThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(nThreads), this);

        return executorService;
    }

    public JobRunnerManager() {
        createExecutorService(DEFAULT_FLOW_NUM_JOB_TREADS);
    }

    public void submitJob(AbstractJob job) throws Exception {
        Runnable r = createJobRunner(job);
        final Future<?> future = this.executorService.submit(r);
    }

    private JobRunner createJobRunner(AbstractJob job) {
        return new JobRunner(job);
    }

    @Override
    public void beforeExecute(Runnable r) {
    }

    @Override
    public void afterExecute(Runnable r) {
    }
}
