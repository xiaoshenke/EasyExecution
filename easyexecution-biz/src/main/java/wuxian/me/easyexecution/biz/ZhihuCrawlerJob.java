package wuxian.me.easyexecution.biz;

import wuxian.me.easyexecution.core.executor.AbstractJob;

/**
 * Created by wuxian on 10/12/2017.
 * 1 http request
 * 2 parsing data
 * 2.1 could be anti-crawlered
 * 2.2 if success ,save to  ??
 */
public class ZhihuCrawlerJob extends AbstractJob {

    private boolean isCanceled = false;

    @Override
    public void cancel() throws Exception {
        isCanceled = true;
    }

    public void run() throws Exception {
        if (isCanceled) {
            ;
        }


        if (isCanceled) {
            ;
        }
    }
}
