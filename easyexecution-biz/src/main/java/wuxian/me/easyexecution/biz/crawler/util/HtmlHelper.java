package wuxian.me.easyexecution.biz.crawler.util;

/**
 * Created by wuxian on 7/1/2018.
 */
public class HtmlHelper {

    private HtmlHelper() {
    }

    public static String removeHtmlContent(String origin) {
        if (origin == null || origin.length() == 0) {
            return origin;
        }

        return origin.replaceAll("&nbsp;", "");
    }
}
