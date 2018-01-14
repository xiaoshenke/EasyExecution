package wuxian.me.easyexecution.biz.crawler.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 14/1/2018.
 */
public class CrawlerWatcherTest {

    @Test
    public void testFetchProxy() throws Exception {
        CrawlerWatcher.initThriftClient("localhost", 9090);
        CrawlerWatcher.notifyFetchProxy("host", 1);
        while (true) {

        }

    }

    @Test
    public void testNotInitThriftClient() throws Exception {
        //CrawlerWatcher.initThriftClient("localhost", 9090);
        CrawlerWatcher.notifyFetchProxy("host", 1);
        while (true) {

        }

    }
}