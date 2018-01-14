package wuxian.me.easyexecution.biz;

import wuxian.me.easyexecution.biz.crawler.BaseCrawerJob;
import wuxian.me.easyexecution.biz.crawler.sougou.WechatJob;
import wuxian.me.easyexecution.biz.crawler.util.CrawlerWatcher;
import wuxian.me.easyexecution.biz.crawler.util.FileUtil;
import wuxian.me.easyexecution.biz.crawler.util.ProxyManager;

/**
 * Created by wuxian on 5/12/2017.
 */
public class Global {

    public static void init() {
        initLog4j();

        ProxyManager.setProxyLimit(BaseCrawerJob.getHost(WechatJob.class), 10);

        ProxyManager.init();

        CrawlerWatcher.initThriftClient("localhost", 9090);
    }

    //
    private static void initLog4j() {
        org.apache.log4j.PropertyConfigurator.configure(FileUtil.getCurrentPath() + "/conf/log4j.properties");
    }
}
