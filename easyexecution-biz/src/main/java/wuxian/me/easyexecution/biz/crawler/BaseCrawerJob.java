package wuxian.me.easyexecution.biz.crawler;

import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.biz.crawler.util.BytesCharsetDetector;
import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.net.HttpURLConnection;
import java.net.Proxy;

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

    private Proxy usedProxy = null;

    //记录当前使用的代理
    protected void setCurrentProxy(Proxy proxy) {
        this.usedProxy = proxy;
    }

    public Proxy getUsedProxy() {
        return usedProxy;
    }

    public static String getHost(Class claz) {
        if (claz == null) {
            return null;
        }
        Host host = (Host) claz.getAnnotation(Host.class);
        return host == null ? null : host.host();

    }
}
