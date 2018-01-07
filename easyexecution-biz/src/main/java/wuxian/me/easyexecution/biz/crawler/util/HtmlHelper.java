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

        return origin.replaceAll("&nbsp;", "").replaceAll("amp;", "");
    }

    public static String extractLink(String origin) {
        if (origin == null || origin.length() == 0) {
            return origin;
        }

        String href = "href=\"";
        int begin = origin.indexOf(href);
        if (begin == -1) {
            return null;
        }

        int end = origin.indexOf("\"", begin + href.length());
        if (end == -1) {
            return null;
        }

        return origin.substring(begin + href.length(), end);
    }
}
