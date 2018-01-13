package wuxian.me.easyexecution.biz.crawler;

import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.biz.crawler.sougou.WechatJob;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 14/1/2018.
 */
public class BaseCrawerJobTest {

    @Test
    public void testHost() {
        Host host = WechatJob.class.getAnnotation(Host.class);

        if (host == null) {
            System.out.println("host empty");
        } else {
            System.out.println(host.host());
        }
    }

}