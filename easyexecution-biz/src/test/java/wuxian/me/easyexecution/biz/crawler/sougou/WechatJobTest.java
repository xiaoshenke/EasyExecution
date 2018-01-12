package wuxian.me.easyexecution.biz.crawler.sougou;

import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.wechat.PublicAccountPageJob;
import wuxian.me.easyexecution.core.event.Event;
import wuxian.me.easyexecution.core.event.EventHandler;
import wuxian.me.easyexecution.core.event.EventType;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 7/1/2018.
 */
public class WechatJobTest {

    @Test
    public void testGetWechatId() throws Exception {
        String s = "南京有个号";

        JobRunnerManager manager = new JobRunnerManager();
        manager.addEventHandler(new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                if (event.getType() == EventType.JOB_FINISHED) {
                    Object o = event.getData();
                    if (o instanceof WechatJob) {
                        if (((WechatJob) o).isGetWxid()) {
                            System.out.println("wxid: " + ((WechatJob) o).getResult());
                        }
                    }
                }
            }
        });
        manager.submitJob(new WechatJob(s));
        while (true) {

        }
    }

    @Test
    public void testWechat() throws Exception {

        String wxid = "oIWsFt91au_OOXwbA0gmDkgpIYE8";

        String s = "南京有个号";

        JobRunnerManager manager = new JobRunnerManager();
        manager.addEventHandler(new EventHandler() {
            @Override
            public void handleEvent(Event event) {
                if (event.getType() == EventType.JOB_FINISHED) {
                    Object o = event.getData();
                    if (o instanceof WechatJob) {
                        if (((WechatJob) o).isGetWxid()) {
                            System.out.println("wxid: " + ((WechatJob) o).getResult());
                        } else {
                            System.out.println("urls: " + ((WechatJob) o).getResult().toString());
                        }
                    }
                }
            }
        });
        manager.submitJob(new WechatJob(s, wxid));
        while (true) {

        }
    }

}