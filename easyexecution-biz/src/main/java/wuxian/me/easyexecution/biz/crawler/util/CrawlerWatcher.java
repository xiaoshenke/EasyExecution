package wuxian.me.easyexecution.biz.crawler.util;

import java.net.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by wuxian on 14/1/2018.
 * 负责监控爬虫的运作情况(比如被屏蔽),维护代理池(的稳定？),处理失效某个代理等操作
 * 在以下情况的时候,crawlerWatcher会申请新的代理
 * 1 初始化
 * 2 被通知需要请求的时候
 */
public class CrawlerWatcher {

    private CrawlerWatcher() {

    }

    private static Map<String, AtomicBoolean> fetchMap = new HashMap<>();

    private static AtomicBoolean isInitFetching = new AtomicBoolean(false);

    //TODO
    public static void initProxyWith(Map<String, Integer> config) {
        ;
    }


    public static boolean notifyFetchProxy(String host, int num) {
        if (isInitFetching.get() || num <= 0) {
            return false;
        }

        //正在请求中 直接返回false
        if (fetchMap.containsKey(host) && fetchMap.get(host).get()) {
            return false;
        }
        fetchMap.put(host, new AtomicBoolean(true));

        //TODO: 请求proxy
        return true;
    }

    //TODO: 找到所有使用该proxy的任务 暂停|强制停止 然后放到重试队列
    public static void onAbandon(Proxy proxy) {
        ;
    }
}
