package wuxian.me.easyexecution.biz.word;

import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.util.List;

/**
 * Created by wuxian on 30/12/2017.
 */
public class SegAndStatisticJob extends AbstractJob {

    private SegmentJob segmentJob;

    public SegAndStatisticJob(String string) {
        this(string, true);
    }

    public SegAndStatisticJob(String string, boolean hasStopword) {

        this.segmentJob = new SegmentJob(hasStopword, string);
    }

    @Override
    public void run() throws Exception {

        this.segmentJob.run();
        List<String> words = (List<String>) segmentJob.getResult();
        if (words == null) {
            return;
        }

        //Todo:
    }
}
