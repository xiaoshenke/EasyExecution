package wuxian.me.easyexecution.biz;

import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.zhihu.ZhihuCrawlerJob;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

/**
 * Created by wuxian on 11/12/2017.
 */
public class ZhihuCrawlerJobTest {

    @Test
    public void testCrawler() throws Exception {
        new JobRunnerManager().submitJob(new ZhihuCrawlerJob());
        while (true) {

        }
    }
}