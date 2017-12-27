package wuxian.me.easyexecution.biz;

import wuxian.me.easyexecution.biz.crawler.util.FileUtil;

/**
 * Created by wuxian on 5/12/2017.
 */
public class Global {

    public static void init() {
        initLog4j();
    }

    //
    private static void initLog4j() {
        org.apache.log4j.PropertyConfigurator.configure(FileUtil.getCurrentPath()+"/conf/log4j.properties");
    }
}
