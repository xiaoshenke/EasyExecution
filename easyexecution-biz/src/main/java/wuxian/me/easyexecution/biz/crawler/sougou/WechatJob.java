package wuxian.me.easyexecution.biz.crawler.sougou;

import okhttp3.HttpUrl;
import org.apache.commons.io.IOUtils;
import org.htmlparser.Node;
import org.htmlparser.Parser;
import org.htmlparser.Tag;
import org.htmlparser.filters.HasAttributeFilter;
import org.htmlparser.tags.Bullet;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;
import wuxian.me.easyexecution.biz.crawler.BaseCrawerJob;
import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.biz.crawler.annotation.URLPattern;
import wuxian.me.easyexecution.biz.crawler.util.*;
import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static wuxian.me.easyexecution.biz.crawler.util.ParsingUtil.firstChildIfNullThrow;

/**
 * Created by wuxian on 7/1/2018.
 */
@Host(host = "weixin.sogou.com")
@URLPattern
public class WechatJob extends BaseCrawerJob {

    private String wechatName;

    private String wxid;
    private int page;
    private boolean isGetWxid = false;

    List<String> urls = new ArrayList<>();

    public WechatJob(String wechatName, String wxid, int page) {
        this.wechatName = wechatName;
        this.wxid = wxid;
        this.page = page;
    }

    public WechatJob(String wechatName, String wxid) {
        this(wechatName, wxid, 1);
    }

    public WechatJob(String wechatName) {
        this(wechatName, null);
    }

    public boolean isGetWxid() {
        return isGetWxid;
    }

    private void getWxidRun() throws Exception {

        String url = "http://weixin.sogou.com/weixin?type=2&ie=utf8&s_from=input&_sug_=n&_sug_type_=&w=01019900&sut=4303&sst0=1515321528543&lkt=1%2C1515321528441%2C1515321528441&query=";
        url = url + wechatName;
        Map<String, String> pro = new HashMap<>();
        pro.put("Host", "weixin.sogou.com");
        pro.put("Connection", "keep-alive");
        pro.put("User-Agent", UserAgentManager.getAgent());

        NetworkUtil.NetResponse res = null;
        boolean success = true;

        try {
            res = NetworkUtil.sendHttpRequest(url, "GET", pro);
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        if (!success) {
            //TODO:
            return;
        }

        if (res.retCode != 200) {
            //TODO:
            return;
        }

        try {
            parseRealData(res.body);
        } catch (ParserException e) {
            ;
        }
    }

    private void getNormalRun() {

        HttpUrl.Builder builder = HttpUrl.parse("http://weixin.sogou.com/weixin?type=2&ie=utf&tsn=0&ft=&et=&interation=")
                .newBuilder();
        builder.addQueryParameter("page", String.valueOf(page));
        builder.addQueryParameter("usip", wechatName);
        builder.addQueryParameter("query", wechatName);
        builder.addQueryParameter("wxid", wxid);

        Map<String, String> pro = new HashMap<>();
        pro.put("Host", "weixin.sogou.com");
        pro.put("Connection", "keep-alive");
        pro.put("User-Agent", UserAgentManager.getAgent());
        pro.put("Referer", builder.toString()); //sogou will carefully check Referer!

        NetworkUtil.NetResponse res = null;
        boolean success = true;

        try {
            res = NetworkUtil.sendHttpRequest(builder.toString(), "GET", pro);
        } catch (IOException e) {
            success = false;
            e.printStackTrace();
        } catch (Exception e) {
            success = false;
            e.printStackTrace();
        }

        if (!success) {
            //TODO:
            return;
        }

        if (res.retCode != 200) {
            //TODO:
            return;
        }

        try {
            parseRealData(res.body);
        } catch (ParserException e) {
            ;
        }

    }

    @Override
    public void run() throws Exception {
        if (wxid == null) {
            isGetWxid = true;
        }

        if (isGetWxid) {
            getWxidRun();
        } else {
            getNormalRun();
        }
    }

    private boolean parseWxidItem(Node node) throws ParserException {
        Node id = ParsingUtil.firstChildOfTypeAndContent(node.getChildren(), Tag.class, "class=\"account", true);

        String name = id.toPlainTextString().trim();
        System.out.println("name: " + name);
        if (!name.equals(this.wechatName)) {
            return false;
        }

        String wxid = "i=\"";
        int begin = id.getText().indexOf(wxid);
        if (begin == -1) {
            return false;
        }

        int end = id.getText().indexOf("\"", begin + wxid.length());
        if (end == -1) {
            return false;
        }

        this.wxid = id.getText().substring(begin + wxid.length(), end);
        return true;
    }

    private boolean parseItem(Node node) throws ParserException {

        if (isGetWxid) {
            return parseWxidItem(node);
        }

        Node id = ParsingUtil.firstChildOfTypeAndContent(node.getChildren(), Tag.class, "class=\"account", true);

        String name = id.toPlainTextString().trim();
        System.out.println("name: " + name);
        if (!name.equals(this.wechatName)) {
            return false;
        }

        Node title = ParsingUtil.firstChildOfTypeAndContent(node.getChildren(), Tag.class, "data-share", true);
        String link = HtmlHelper.removeHtmlContent(HtmlHelper.extractLink(title.getText()));
        urls.add(link);
        System.out.println("link: " + link);
        return true;

    }

    private void parseRealData(String data) throws ParserException {

        Parser parser = new Parser(data);
        parser.setEncoding("utf-8");
        HasAttributeFilter filter = new HasAttributeFilter("class", "news-list");
        Node node = firstChildIfNullThrow(parser.extractAllNodesThatMatch(filter));

        //NodeLogUtil.printChildrenOfNode(node);
        NodeList list = ParsingUtil.childrenOfType(node.getChildren(), Bullet.class);

        for (int i = 0; i < list.size(); i++) {

            Node n = list.elementAt(i);
            boolean b = parseItem(n);

            if (isGetWxid && b) {
                break;
            }
        }
    }

    public Object getResult() {
        if (isGetWxid) {
            return wxid;
        } else {
            return urls;
        }
    }
}
