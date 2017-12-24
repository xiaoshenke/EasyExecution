package wuxian.me.easyexecution.biz.word;


import wuxian.me.easyexecution.core.executor.AbstractJob;

import java.util.List;

/**
 * Created by wuxian on 24/12/2017.
 */
public class SegmentJob extends AbstractJob {

    private boolean hasStopWord;

    private String words;

    public SegmentJob(boolean hasStopword, String words) {
        this.hasStopWord = hasStopword;
        this.words = words;
    }

    public SegmentJob(String words) {
        this(true, words);
    }

    @Override
    public void run() throws Exception {

        MaximumMatching seg = new MaximumMatching(hasStopWord);
        List<String> result = seg.seg(words);

        //Todo: callback?

    }
}
