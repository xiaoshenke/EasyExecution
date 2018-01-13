package wuxian.me.easyexecution.biz.crawler;

import org.apache.commons.io.IOUtils;
import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.biz.crawler.annotation.URLPattern;
import wuxian.me.easyexecution.biz.crawler.util.*;
import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by wuxian on 10/12/2017.
 * 1 http request
 * 2 parsing data
 * 2.1 could be anti-crawlered
 * 2.2 if success ,save to  ??
 */
@Host(host = "www.zhihu.com")
@URLPattern(regex = "https://www.zhihu.com/question/[0-9]+/answer/[0-9]+")
public class ZhihuCrawlerJob extends AbstractJob {

    private final String host = "www.zhihu.com";

    private boolean isCanceled = false;

    @Override
    public void cancel() throws Exception {
        isCanceled = true;
    }

    public void run() throws Exception {
        if (isCanceled) {
            return;
        }

        HttpURLConnection conn = null;
        String url = "https://www.zhihu.com/question/67080789/answer/252374212";

        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Cookie", CookieManager.get("zhihu", FileUtil.getCurrentPath() + "/cookie/zhihu_cookie"));
            conn.setRequestProperty("Host", "www.zhihu.com");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("X-UDID", "AEBAae4zoQmPTh_TXP4owAhRtunUxzWm_Xc=");
            conn.setRequestProperty("X-API-VERSION", "3.0.40");
            conn.setRequestProperty("authorization", "oauth c3cef7c66a1843f8b3a9e6a1e3160e20");
            conn.setRequestProperty("User-Agent", UserAgentManager.getAgent());
            int status = conn.getResponseCode();
            byte[] responseBytes = IOUtils.toByteArray(conn.getInputStream());

            System.out.println("status code: " + status);
        } catch (Exception e) {

            System.out.println("exception: " + e.getMessage());

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        if (isCanceled) {
            ;
        }
    }
}
