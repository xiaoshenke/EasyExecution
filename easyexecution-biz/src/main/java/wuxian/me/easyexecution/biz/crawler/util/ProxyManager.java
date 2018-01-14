package wuxian.me.easyexecution.biz.crawler.util;

import java.net.Proxy;
import java.util.*;


/**
 * Created by wuxian on 13/1/2018.
 * 提供代理管理
 * host和代理成 1对多 关系 --> 不对 应该是 多对多 的关系..同样1个代理也可以给不同的host用 节约代理资源
 * 而禁用的时候 仅仅失效某个host对应的proxy
 * 代理和agent,cookie等成 1对1 关系
 */
public class ProxyManager {

    private ProxyManager() {
    }

    private static int DEFAULT_PROXY_CONFIG = 2;

    private static Map<String, Integer> config = new HashMap<>();
    private static Map<String, List<Proxy>> map = new HashMap<>();

    //没有host指定的proxy 用于测试 --> 维护这个真有点麻烦...
    private static Proxy noHostProxy = null;

    //初始化 --> 请求n个proxy
    public static void init() {
        CrawlerWatcher.initProxyWith(config);

        //初始化proxy map
        Iterator<Map.Entry<String, Integer>> iterator = config.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            map.put(entry.getKey(), new ArrayList<>(entry.getValue()));
        }

    }


    public static void removeProxy(String host, Proxy proxy) {
        if (host == null || host.length() == 0 || proxy == null) {
            return;
        }
        List<Proxy> list = map.get(host);
        if (list.contains(proxy)) {
            list.remove(proxy);
        }

        if (!config.containsKey(host)) {
            return;
        }

        if (list.size() != config.get(host)) {
            CrawlerWatcher.notifyFetchProxy(host, config.get(host) - list.size());
        }

    }

    //这里客户端的getProxy操作一定是个无限重试的过程
    //要注意一个proxy挂掉的时候 一定要暂停|强制停止所有使用这个proxy的job
    //这里host允许为空 为空的一般是用于测试的
    public static Proxy getProxyBy(String host) {
        if (!map.containsKey(host)) {
            config.put(host, DEFAULT_PROXY_CONFIG);
            CrawlerWatcher.notifyFetchProxy(host, DEFAULT_PROXY_CONFIG);
            return null;
        }

        List<Proxy> list = map.get(host);
        if (list.size() == 0) {
            CrawlerWatcher.notifyFetchProxy(host, config.get(host));

            return null;
        }
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    //设置某个host最多能有几个host 事实上也就限制了对于这个host最多有几个ip在访问
    public static void setProxyLimit(String host, int limit) {
        if (host == null || host.length() == 0) {
            return;
        }
        config.put(host, limit);
    }

    public static class HostProxy {
        public String host;
        public Proxy proxy;

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof HostProxy)) return false;

            HostProxy hostProxy = (HostProxy) o;

            if (host != null ? !host.equals(hostProxy.host) : hostProxy.host != null) return false;
            return proxy != null ? proxy.equals(hostProxy.proxy) : hostProxy.proxy == null;

        }

        @Override
        public int hashCode() {
            int result = host != null ? host.hashCode() : 0;
            result = 31 * result + (proxy != null ? proxy.hashCode() : 0);
            return result;
        }
    }

}
