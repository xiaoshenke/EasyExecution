package wuxian.me.easyexecution.biz.crawler.util;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.thrift.TException;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TSSLTransportFactory.TSSLTransportParameters;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import wuxian.me.proxyspider.thrift.proto.TProxy;
import wuxian.me.proxyspider.thrift.proto.ProxyService;

/**
 * Created by wuxian on 14/1/2018.
 * 负责监控爬虫的运作情况(比如被屏蔽),维护代理池(的稳定？),处理失效某个代理等操作
 * 在以下情况的时候,crawlerWatcher会申请新的代理
 * 1 初始化
 * 2 被通知需要请求的时候
 */
public class CrawlerWatcher {

    static ProxyService.Client client;

    private static Map<String, AtomicBoolean> fetchMap = new HashMap<>();

    private static AtomicBoolean isInitFetching = new AtomicBoolean(false);

    public static void initThriftClient(String host, int port) {
        TTransport transport = new TSocket(host, port);
        try {
            transport.open();
            TProtocol protocol = new TBinaryProtocol(transport);
            client = new ProxyService.Client(protocol);
        } catch (Exception e) {

        }
    }

    public static void initProxyWith(Map<String, Integer> config) {
        if (config == null || config.size() == 0) {
            return;
        }

        Iterator<Map.Entry<String, Integer>> iterator = config.entrySet().iterator();

        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            notifyFetchProxy(entry.getKey(), entry.getValue());
        }
    }


    public static boolean notifyFetchProxy(String host, final int num) {
        if (client == null) {
            throw new RuntimeException("you haven't call initThriftClient()!");
        }

        if (isInitFetching.get() || num <= 0) {
            return false;
        }

        //正在请求中 直接返回false
        if (fetchMap.containsKey(host) && fetchMap.get(host).get()) {
            return false;
        }
        fetchMap.put(host, new AtomicBoolean(true));
        new Thread(new FetchRunnable(host, num)).start();
        return true;
    }


    public static void onAbandon(String host, Proxy proxy) {

        UserAgentManager.removeProxy(host, proxy);

        ProxyManager.removeProxy(host, proxy);

        CookieManager.removeProxy(host, proxy);

        //TODO: 找到所有使用该proxy的任务 暂停|强制停止 然后放到重试队列
    }

    private static class FetchRunnable implements Runnable {

        private String host;
        int num;

        FetchRunnable(String host, int num) {
            this.host = host;
            this.num = num;
        }

        @Override
        public void run() {
            try {
                List<TProxy> list = client.getProxy(num);
                System.out.println("notifyFetchProxy result: " + list.toString());
            } catch (Exception e) {

            } finally {
                fetchMap.put(host, new AtomicBoolean(false));
            }
        }
    }

    private CrawlerWatcher() {

    }

}
