package wuxian.me.easyexecution.biz.word;

import wuxian.me.easyexecution.core.executor.AbstractJob;
import wuxian.me.segmentation.MaxLengthMatching;

import java.util.List;

/**
 * Created by wuxian on 24/12/2017.
 */
public class SegmentJob extends AbstractJob {

    private boolean hasStopWord;

    private String words;

    List<String> result;

    public SegmentJob(boolean hasStopword, String words) {
        this.hasStopWord = hasStopword;
        this.words = words;
    }

    public SegmentJob(String words) {
        this(true, words);
    }

    @Override
    public void run() throws Exception {

        MaxLengthMatching seg = new MaxLengthMatching(hasStopWord);
        result = seg.seg(words);

    }

    public Object getResult() {
        return result;
    }
}
