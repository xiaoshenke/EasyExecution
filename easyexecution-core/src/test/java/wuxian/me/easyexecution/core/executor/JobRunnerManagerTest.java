package wuxian.me.easyexecution.core.executor;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 9/12/2017.
 */
public class JobRunnerManagerTest {
    @Test
    public void testSubmit() throws Exception{
        new JobRunnerManager().submitJob(new EchoJob());
    }

    @Test
    public void testCancle() throws Exception {
        JobRunnerManager manager = new JobRunnerManager();
        CancelableJob job = new CancelableJob();
        manager.submitJob(job);
        manager.cancelByExecId(job.getExecId());
        while (true) {
        }

    }

    private class CancelableJob extends AbstractJob {
        private boolean isCanceled = false;

        @Override
        public void cancel() throws Exception {
            isCanceled = true;
        }

        @Override
        public void run() throws Exception {
            System.out.println("cancelable job stage1");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {

            }
            if (isCanceled) {
                System.out.println("cancelable job is canceled");
            } else {
                System.out.println("cancelable job not canceled");
            }
        }
    }

    private class EchoJob extends AbstractJob {
        public void run() throws Exception {
            System.out.println("echo job");
        }
    }
}