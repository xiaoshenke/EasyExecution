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
    private class EchoJob extends AbstractJob {

        public void run() throws Exception {
            System.out.println("echo job");
        }
    }
}