package wuxian.me.easyexecution.biz.crawler.sougou;

import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.wechat.PublicAccountPageJob;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 7/1/2018.
 */
public class WechatJobTest {

    @Test
    public void testWechat() throws Exception {
        String s = "南京有个号";
        new JobRunnerManager().submitJob(new WechatJob(s));
        while (true) {

        }
    }

}