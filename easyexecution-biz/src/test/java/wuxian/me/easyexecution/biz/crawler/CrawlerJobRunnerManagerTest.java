package wuxian.me.easyexecution.biz.crawler;

import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 15/1/2018.
 */
public class CrawlerJobRunnerManagerTest {

    @Test
    public void testRate() throws Exception {
        JobRunnerManager manager = new CrawlerJobRunnerManager(false);
        BaseCrawerJob job = new TestCrawlerJob();
        job.setRate(1000);  //设置频率

        for (int i = 0; i < 10; i++) {
            manager.submitJob(job);
        }
        while (true) {

        }
    }

    @Host(host = "testHost")
    private class TestCrawlerJob extends BaseCrawerJob {

        final SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        @Override
        public void run() throws Exception {
            System.out.println("CrawerJob.run time: " + formatter.format(new Date())
                    + " thread: " + Thread.currentThread().getName());
        }
    }

}