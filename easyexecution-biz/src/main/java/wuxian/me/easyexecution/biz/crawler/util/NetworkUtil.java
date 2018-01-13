package wuxian.me.easyexecution.biz.crawler.util;

import org.apache.commons.io.IOUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.Iterator;
import java.util.Map;

import static wuxian.me.easyexecution.biz.crawler.BaseCrawerJob.resolveEncoding;

/**
 * Created by wuxian on 13/1/2018.
 */
public class NetworkUtil {

    private NetworkUtil() {
    }

    public static NetResponse sendHttpRequest(String url, String method
            , Map<String, String> properties, boolean useCache) throws MalformedURLException, ProtocolException
            , IOException, UnknownHostException {
        if (url == null || url.length() == 0) {
            throw new RuntimeException("url is empty!");
        }

        if (method == null) {
            method = "GET";
        }

        String responseText = null;
        String encoding = null;
        byte[] responseBytes = null;
        HttpURLConnection conn = null;

        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod(method);
            conn.setUseCaches(useCache);

            if (properties != null) {
                Iterator<Map.Entry<String, String>> iterator = properties.entrySet().iterator();
                while (iterator.hasNext()) {
                    Map.Entry<String, String> entry = iterator.next();
                    conn.setRequestProperty(entry.getKey(), entry.getValue());
                }
            }

            int status = conn.getResponseCode();

            responseBytes = IOUtils.toByteArray(conn.getInputStream());

            if (responseBytes == null) {
                throw new RuntimeException("no data avaliable in http response!");
            }
            encoding = resolveEncoding(responseBytes, conn);
            if (encoding == null || encoding.isEmpty()) {
                throw new RuntimeException("Cannot Detected Charset of : " + url);
            }

            try {
                responseText = new String(responseBytes, encoding);
            } catch (UnsupportedEncodingException e) {
                System.out.println("crawler page:" + url + " UnsupportedEncodingException:" + e.getMessage());
                throw new RuntimeException("can't decode reponse data");
            }

            NetResponse response = new NetResponse();
            response.retCode = status;
            response.body = responseText;
            return response;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }
    }

    public static NetResponse sendHttpRequest(String url, String method
            , Map<String, String> properties) throws MalformedURLException, ProtocolException
            , IOException, UnknownHostException {
        return sendHttpRequest(url, method, properties, true);
    }

    public static class NetResponse {
        public int retCode;
        public String body;
        //Exception e;
    }
}
