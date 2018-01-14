package wuxian.me.easyexecution.biz.crawler.util;

import com.sun.istack.internal.Nullable;

import java.net.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import wuxian.me.easyexecution.biz.crawler.util.ProxyManager.HostProxy;

/**
 * Created by wuxian on 3/6/2017.
 */
public class CookieManager {

    private static Map<String, String> cookieList = new HashMap<String, String>();
    private static Map<HostProxy, String> cookieMap = new HashMap<>();

    private static Map<HostProxy, List<String>> removedCookie = new HashMap<>();

    public static String get(String host, Proxy proxy, String key, String cookiePath) throws RuntimeException {
        if (host == null || host.length() == 0 || proxy == null) {
            return get(key, cookiePath);
        }
        HostProxy hostProxy = new HostProxy();
        hostProxy.host = host;
        hostProxy.proxy = proxy;

        if (cookieMap.containsKey(hostProxy)) {
            return cookieMap.get(hostProxy);
        }

        String cookie = get(key, cookiePath);
        if (removedCookie.containsKey(host)) {
            List<String> list = removedCookie.get(hostProxy); //可能导致资源枯竭
            while (cookie != null && list.contains(cookie)) {
                cookie = get(key, cookiePath);
            }
        }
        if (cookie != null) {
            cookieMap.put(hostProxy, cookie);
        }
        return cookie;
    }

    public static boolean removeProxy(String host, Proxy proxy) {
        if (host == null || host.length() == 0 || proxy == null) {
            return true;
        }
        HostProxy hostProxy = new HostProxy();
        hostProxy.host = host;
        hostProxy.proxy = proxy;

        if (cookieMap.containsKey(hostProxy)) {
            String ag = cookieMap.get(hostProxy);
            if (!removedCookie.containsKey(host)) {
                removedCookie.put(hostProxy, new ArrayList<>());
            }
            List<String> list = removedCookie.get(host);
            if (!list.contains(ag)) {
                list.add(ag);
            }
            removedCookie.put(hostProxy, list);

            cookieMap.remove(hostProxy);
            return true;
        }
        return false;
    }

    @Nullable
    public static String get(String key, String cookiePath) throws RuntimeException {
        if (key == null || key.length() == 0) {
            throw new RuntimeException("key is empty!");
        }

        if (cookieList.containsKey(key)) {
            return cookieList.get(key);
        }
        if (cookiePath == null || cookiePath.length() == 0) {
            throw new RuntimeException("cookiePath is empty!");
        }
        String cookie = FileUtil.readFromFile(cookiePath);
        if (cookie == null || cookie.length() == 0) {
            throw new RuntimeException("cookie is empty!");
        }

        cookieList.put(key, cookie);
        return cookie;
    }

    public static void clear() {
        cookieList.clear();
    }

    private CookieManager() {
    }

}
