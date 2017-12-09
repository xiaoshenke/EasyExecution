package wuxian.me.easyexecution.core.executor;

import java.util.Properties;

public abstract class AbstractJob implements Job {

    private String execId;
    private Double progress;

    private String expression;

    private String type;

    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getExpression() {
        return expression;
    }

    public AbstractJob() {

    }

    public void setExecId(String execId) {
        this.execId = execId;
    }

    private long startTime;
    private long endTime;

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }


    public void setProgress(final double progress) {
        this.progress = progress;
    }

    public String getExecId() {
        return execId;
    }

    @Override
    public abstract void run() throws Exception;

    @Override
    public void cancel() throws Exception {
        throw new RuntimeException("Job " + this.execId + " does not support cancellation!");
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
