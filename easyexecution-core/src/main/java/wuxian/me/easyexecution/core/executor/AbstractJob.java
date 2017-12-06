package wuxian.me.easyexecution.core.executor;

import java.util.Properties;

public abstract class AbstractJob implements Job {

    private String id;
    private Double progress;

    public AbstractJob(String id) {
        this.id = id;
    }

    /*
    private long startTime;
    private long endTime;
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }
    */

    public void setProgress(final double progress) {
        this.progress = progress;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public abstract void run() throws Exception;

    @Override
    public void cancel() throws Exception {
        throw new RuntimeException("Job " + this.id + " does not support cancellation!");
    }

    @Override
    public double getProgress() throws Exception {
        return progress;
    }

    @Override
    public Properties getProperties() {
        return new Properties();
    }

    @Override
    public boolean isCanceled() {
        return false;
    }
}
