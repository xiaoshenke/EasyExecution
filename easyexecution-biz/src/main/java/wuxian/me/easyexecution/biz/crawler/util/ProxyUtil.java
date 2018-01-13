package wuxian.me.easyexecution.biz.crawler.util;

import okhttp3.HttpUrl;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * Created by wuxian on 14/1/2018.
 * TODO: isProxyValid需要实现线程安全 而且注意验证代理的行为可能会称为瓶颈
 */
public class ProxyUtil {

    private ProxyUtil() {
    }

    static {
        init();
    }

    public static boolean isProxyValid(Proxy proxy) {
        if (proxy == null) {
            return false;
        }
        try {
            boolean ret = validateIfIpSwitchedSuccess(proxy);
            return ret;
        } catch (InterruptedException e1) {

            //LogManager.error("isIpSwitchedSuccess interruptedException");
            return false;
        } catch (ExecutionException e) {

            //LogManager.error("isIpSwitchedSuccess executionException");
            return false;
        }
    }

    public static boolean validateIfIpSwitchedSuccess(final Proxy proxy)
            throws InterruptedException, ExecutionException {
        FutureTask<String> future = getProxyValidatorFuture();
        new Thread(future).start();
        if (future.get() == null) {
            //LogManager.error("future.get is null");
            return false;
        }
        //LogManager.info("returned html:" + future.get());
        InetSocketAddress address = (InetSocketAddress) proxy.address();
        boolean b = future.get().contains(address.getHostName());
        return b;
    }

    public static FutureTask<String> getProxyValidatorFuture() {
        return new FutureTask<String>(getProxyValidatorCallable());
    }

    private static Callable<String> getProxyValidatorCallable() {
        return new Callable<String>() {
            public String call() throws Exception {
                HttpUrl.Builder urlBuilder = HttpUrl.parse("http://city.ip138.com/ip2city.asp").newBuilder();

                try {
                    NetworkUtil.NetResponse response = NetworkUtil.sendHttpRequest(urlBuilder.toString(), "GET", pros);

                    if (response.retCode == 200) {
                        return response.body;
                    }
                    return null;
                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }
        };
    }

    private static boolean inited = false;

    private static Map<String, String> pros = new HashMap<>();

    private static void buildValidProxyRequest() {

        pros.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_0) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/56.0.2924.87 Safari/537.36");
        pros.put("Host", "city.ip138.com");
        pros.put("Cookie", "ASPSESSIONIDQSRBDCTB=KEBMKPPEHJPLEPEBEPJIBNKN");
        pros.put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
        pros.put("Connection", "close");
        pros.put("Pragma", "no-cache");
        pros.put("Accept-Encoding", "gzip, deflate");
        pros.put("Accept-Language", "zh-CN,zh;q=0.8,en;q=0.6");
        pros.put("Upgrade-Insecure-Requests", "1");
        //NetworkUtil.sendHttpRequest(urlBuilder.toString(),"GET",pros);
    }

    public static void init() {
        if (inited) {
            return;
        }
        inited = true;
        buildValidProxyRequest();
    }


}
