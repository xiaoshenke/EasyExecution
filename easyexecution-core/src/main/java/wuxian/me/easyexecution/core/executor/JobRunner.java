package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.executor.id.JobIdFactory;

public class JobRunner implements Runnable {

    public JobRunner(AbstractJob job) {
        this.job = job;
    }

    public void setExecId(long execId) {
        if (this.job != null) {
            this.job.setExecId(String.valueOf(execId));
        }
    }

    private boolean isCanceled = false;

    public boolean isCanceled() {
        return isCanceled;
    }

    public void canel() {
        this.isCanceled = true;

        if (job != null) {
            try {
                job.cancel();
            } catch (Exception e) {
            }
        }
    }

    @Override
    public void run() {
        try {
            realRun();

        } catch (Exception e) {
            ;
        }

        //Todo: logging
        if (isCanceled) {

        }

    }

    private AbstractJob job;

    public String getExecId() {
        return job == null ? "-1" : job.getExecId();
    }

    private void realRun() throws Exception {
        job.setStartTime(System.currentTimeMillis());
        this.job.run();
        job.setEndTime(System.currentTimeMillis());
    }

}
