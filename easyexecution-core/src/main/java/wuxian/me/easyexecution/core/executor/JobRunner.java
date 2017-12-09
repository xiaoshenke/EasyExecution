package wuxian.me.easyexecution.core.executor;

import wuxian.me.easyexecution.core.executor.id.JobIdFactory;

public class JobRunner implements Runnable {

    public JobRunner(AbstractJob job) {
        this.job = job;
    }

    @Override
    public void run() {
        try {
            realRun();
        } catch (Exception e) {
            ;
        }

        //Todo: logging
    }
    private AbstractJob job;

    private void realRun() throws Exception {
        job.setId(String.valueOf(JobIdFactory.getInstance().getGenerator()));
        job.setStartTime(System.currentTimeMillis());
        this.job.run();
        job.setEndTime(System.currentTimeMillis());
    }

}
