package wuxian.me.easyexecution.biz.crawler.wechat;

import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.ParagraphTag;
import org.htmlparser.tags.Span;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import wuxian.me.easyexecution.biz.crawler.BaseCrawerJob;
import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.biz.crawler.annotation.URLPattern;
import wuxian.me.easyexecution.biz.crawler.util.*;
import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;

import static wuxian.me.easyexecution.biz.crawler.util.ParsingUtil.childrenOfType;
import static wuxian.me.easyexecution.biz.crawler.util.ParsingUtil.firstChildIfNullThrow;

/**
 * Created by wuxian on 7/1/2018.
 */
@Host(host = "mp.weixin.qq.com")
@URLPattern
public class PublicAccountPageJob extends BaseCrawerJob {

    private String title;
    private String content;

    private String url;

    public PublicAccountPageJob(String url) {
        this.url = url;
    }

    @Override
    public void run() throws Exception {
        HttpURLConnection conn = null;

        String responseText = null;
        String encoding = null;
        byte[] responseBytes = null;
        try {
            conn = (HttpURLConnection) (new URL(url)).openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Host", "mp.weixin.qq.com");
            conn.setRequestProperty("Connection", "keep-alive");
            conn.setRequestProperty("User-Agent", UserAgentManager.getAgent());
            int status = conn.getResponseCode();
            responseBytes = IOUtils.toByteArray(conn.getInputStream());
            if (status != 200 || responseBytes == null) {
                //Todo
                System.out.println("craw page:" + url + " fail,http code:" + status);
                return;
            }
            encoding = resolveEncoding(responseBytes, conn);
            if (encoding == null || encoding.isEmpty()) {
                //Todo
                System.out.println("Cannot Detected Charset of : " + url);
                return;
            }

        } catch (Exception e) {

            System.out.println("exception: " + e.getMessage());

        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {
            responseText = new String(responseBytes, encoding);
        } catch (UnsupportedEncodingException e) {
            System.out.println("crawler page:" + url + " UnsupportedEncodingException:" + e.getMessage());
            return;
        }

        parseRealData(responseText);
    }

    private void parseTitle(String data) throws ParserException {

        Parser parser = new Parser(data);
        parser.setEncoding("utf-8");
        HasAttributeFilter filter = new HasAttributeFilter("id", "activity-name");
        Node node = firstChildIfNullThrow(parser.extractAllNodesThatMatch(filter));

        title = node.toPlainTextString().trim();
        System.out.println("title: " + title);

    }

    private void parseBody(String data) throws ParserException {

        Parser parser = new Parser(data);
        parser.setEncoding("utf-8");
        HasAttributeFilter filter = new HasAttributeFilter("id", "js_content");
        Node node = firstChildIfNullThrow(parser.extractAllNodesThatMatch(filter));

        NodeList list = childrenOfType(node.getChildren(), ParagraphTag.class);
        //NodeLogUtil.printChildrenOfNode(node);

        StringBuilder builder = new StringBuilder("");
        for (int i = 0; i < list.size(); i++) {
            Node n = list.elementAt(i);
            Node child = ParsingUtil.firstChildOfType(n.getChildren(), Span.class, true);
            if (child != null) {
                String s = HtmlHelper.removeHtmlContent(child.toPlainTextString().trim());
                if (s != null && s.length() != 0) {
                    builder.append(s);
                    builder.append("\n");
                }
            }
        }
        content = builder.toString();
        System.out.println("content: " + content);

    }

    private void parseRealData(String data) throws ParserException {

        parseTitle(data);
        parseBody(data);

    }


}
