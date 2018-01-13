package wuxian.me.easyexecution.biz.crawler;

import wuxian.me.easyexecution.biz.crawler.util.BytesCharsetDetector;
import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.net.HttpURLConnection;

/**
 * Created by wuxian on 7/1/2018.
 */
public abstract class BaseCrawerJob extends AbstractJob {

    public static String resolveEncoding(byte[] bytes, HttpURLConnection connection) {
        String encoding = BytesCharsetDetector.getDetectedCharset(bytes, null);
        if (encoding == null) {
            String contentType = connection.getContentType();
            encoding = BytesCharsetDetector.getCharsetByResponseContentType(contentType);
        }

        return encoding;
    }
}
