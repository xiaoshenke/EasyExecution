package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventHandler;
import wuxian.me.easyexecution.core.executor.dispatch.ConcurrencyDispatcher;
import wuxian.me.easyexecution.core.executor.dispatch.FixedFrequencyDispatcher;
import wuxian.me.easyexecution.core.executor.id.JobIdFactory;
import wuxian.me.easyexecution.core.util.ThreadPoolExecutingListener;
import wuxian.me.easyexecution.core.util.TrackingThreadPool;

import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class JobRunnerManager implements ThreadPoolExecutingListener, EventHandler {

    private static final int DEFAULT_FLOW_NUM_JOB_TREADS = 10;
    private static final long DEFAULT_FIXED_RATE = 3000;

    private final Map<String, JobRunner> runningJobs = new ConcurrentHashMap<>();
    private final Map<String, JobRunner> submitedJobs = new ConcurrentHashMap<>();
    private TrackingThreadPool executorService;

    private TrackingThreadPool createExecutorService(final int nThreads) {
        executorService = new TrackingThreadPool(nThreads, nThreads, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(nThreads), this);
        return executorService;
    }

    public TrackingThreadPool getExecutorService() {
        return executorService;
    }

    private ConcurrencyDispatcher concurrencyDispatcher = new ConcurrencyDispatcher(this);
    private FixedFrequencyDispatcher fixedFrequencyDispatcher = new FixedFrequencyDispatcher(this, DEFAULT_FIXED_RATE);

    public JobRunnerManager() {
        createExecutorService(DEFAULT_FLOW_NUM_JOB_TREADS);
    }

    public void submitJob(AbstractJob job) throws Exception {

        JobRunner jobRunner = createJobRunner(job);
        jobRunner.setExecId(JobIdFactory.getInstance().getGenerator().generateId());

        Properties properties = job.getProperties();
        if (properties == null) {
            properties = new Properties();
        }

        if (!properties.containsKey(AbstractJob.JOB_EXECUTE_TYPE)) {  //Todo: 默认使用ConcurrencyDispatcher取得最快效果
            concurrencyDispatcher.submit(jobRunner);
        } else {
            fixedFrequencyDispatcher.submit(jobRunner);
        }
        this.submitedJobs.put(jobRunner.getExecId(), jobRunner);
    }

    public void cancelByExecId(String execId) {
        if (execId != null && execId.length() != 0) {
            if (runningJobs.containsKey(execId)) {
                runningJobs.get(execId).canel();
                runningJobs.remove(execId);
            }
        }

        concurrencyDispatcher.cancelByExecId(execId);
        fixedFrequencyDispatcher.cancelByExecId(execId);
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
