package wuxian.me.easyexecution.biz.util;

import com.sun.istack.internal.Nullable;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by wuxian on 3/6/2017.
 */
public class CookieManager {

    private static Map<String, String> cookieList = new HashMap<String, String>();

    private CookieManager() {
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
}
