package wuxian.me.easyexecution.biz.crawler.wechat;

import org.junit.Test;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

/**
 * Created by wuxian on 7/1/2018.
 */
public class PublicAccountPageJobTest {

    @Test
    public void testCrawler() throws Exception {

        String url = "https://mp.weixin.qq.com/s?timestamp=1515321551&src=3&ver=1&signature=aPNCMCI1upNDPGts6tXHC1cY85EJOwZqN0OHWIwoqwyLR82yvvoGIKvJt-61bAYSYTLJXEfSXSLryYQhpBB2uzQW4GuPJuX3gO8ZPGldu3Pz8IupXK63qogElpiH7h6l7hz1v-12TwGmJnwSax1nTWA5SvEe2e91U-JAYF321AY=";
        new JobRunnerManager().submitJob(new PublicAccountPageJob(url));
        while (true) {

        }
    }
}