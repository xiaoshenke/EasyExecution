package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.concurrent.Future;

public class JobRunnerManager {

    private TrackingThreadPool executorService;

    private TrackingThreadPool createExecutorService(final int nThreads) {
        return null;
    }

    public void submitJob(final int execId) throws Exception {

        Runnable r = createJobRunner();
        final Future<?> future = this.executorService.submit(r);

    }

    private JobRunner createJobRunner() {
        return null;
    }
}
