package wuxian.me.easyexecution.biz.crawler.util;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 7/1/2018.
 */
public class HtmlHelperTest {

    @Test
    public void testLink() {
        String s = "a target=\"_blank\" href=\"http://mp.w\"";
        System.out.println(HtmlHelper.extractLink(s));
    }

}