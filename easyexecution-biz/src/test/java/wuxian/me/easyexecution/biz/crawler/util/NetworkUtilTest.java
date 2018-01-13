package wuxian.me.easyexecution.biz.crawler.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 13/1/2018.
 */
public class NetworkUtilTest {

    @Test
    public void test1() throws Exception {
        NetworkUtil.sendHttpRequest("http://www.12345", null, null);//UnkownHostException
    }

    @Test
    public void test2() throws Exception {
        NetworkUtil.sendHttpRequest("http://www.baidu.com", null, null);
    }

}