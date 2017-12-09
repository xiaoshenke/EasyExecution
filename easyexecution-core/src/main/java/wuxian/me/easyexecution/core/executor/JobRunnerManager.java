package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventHandler;
import wuxian.me.easyexecution.core.executor.id.JobIdFactory;
import wuxian.me.easyexecution.core.util.ThreadPoolExecutingListener;
import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JobRunnerManager implements ThreadPoolExecutingListener, EventHandler {

    private static final int DEFAULT_FLOW_NUM_JOB_TREADS = 10;

    private final Map<String, JobRunner> runningJobs = new ConcurrentHashMap<>();
    private TrackingThreadPool executorService;

    private TrackingThreadPool createExecutorService(final int nThreads) {
        executorService = new TrackingThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(nThreads), this);

        return executorService;
    }

    public JobRunnerManager() {
        createExecutorService(DEFAULT_FLOW_NUM_JOB_TREADS);
    }

    public void submitJob(AbstractJob job) throws Exception {

        JobRunner r = createJobRunner(job);
        r.setExecId(JobIdFactory.getInstance().getGenerator().generateId());

        final Future<?> future = this.executorService.submit(r);
        this.runningJobs.put(r.getExecId(), r);
    }

    public void cancelByExecId(String execId) {
        if (execId != null && execId.length() != 0) {
            if (runningJobs.containsKey(execId)) {
                runningJobs.get(execId).canel();
                runningJobs.remove(execId);
            }
        }
    }

    //Todo
    public void cancelTypeOf(String type) {
        ;
    }

    //Todo:
    public void resumeTypeOf(String type) {
        ;
    }

    private JobRunner createJobRunner(AbstractJob job) {

        JobRunner runner = new JobRunner(job);
        runner.addEventHandlers(this);
        return runner;
    }

    @Override
    public void beforeExecute(Runnable r) {
    }

    @Override
    public void afterExecute(Runnable r) {
    }

    @Override
    public void handleEvent(Event event) {
        //Todo
    }
}
