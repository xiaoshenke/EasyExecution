package wuxian.me.easyexecution.biz.crawler;

import wuxian.me.easyexecution.biz.crawler.annotation.Host;
import wuxian.me.easyexecution.core.executor.AbstractJob;
import wuxian.me.easyexecution.core.executor.JobRunner;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;
import wuxian.me.easyexecution.core.executor.dispatch.FixedFrequencyDispatcher;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by wuxian on 15/1/2018.
 * 设计:每个host对应一个rate的scheduler 若没有host,则认为是concurrency模式
 */
public class CrawlerJobRunnerManager extends JobRunnerManager {

    private Map<String, FixedFrequencyDispatcher> schedulers = new HashMap<>();

    public CrawlerJobRunnerManager(boolean b) {
        super(b);
    }

    protected void dispatchJobImpl(JobRunner runner) {

        if (!isConcurrencyMode() && runner.getJob() instanceof BaseCrawerJob) {
            BaseCrawerJob crawerJob = (BaseCrawerJob) runner.getJob();
            String host = crawerJob.getHost();
            long rate = crawerJob.getRate();
            if (rate == -1) {
                rate = crawerJob.getRateByAnnotation();
            }

            if (host != null && rate != -1) {
                dispatchByHost(host, rate, runner);
                return;
            }
        }

        concurrencyDispatcher.submit(runner);
        return;
    }

    private void dispatchByHost(String host, long rate, JobRunner runner) {
        if (host == null || host.length() == 0) {
            return;
        }
        if (!schedulers.containsKey(host)) {
            FixedFrequencyDispatcher dispatcher = new FixedFrequencyDispatcher(this, rate);
            schedulers.put(host, dispatcher);
            dispatcher.submit(runner);
            return;
        }

        FixedFrequencyDispatcher dispatcher = schedulers.get(host);
        dispatcher.setRate(rate);
        dispatcher.submit(runner);

    }

}
