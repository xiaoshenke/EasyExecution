package wuxian.me.easyexecution.biz.word;

import wuxian.me.easyexecution.biz.word.core.Dictionary;
import wuxian.me.easyexecution.biz.word.core.DictionaryTrie;
import wuxian.me.easyexecution.biz.word.util.RecognitionUtil;
import wuxian.me.easyexecution.biz.word.util.WordsLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by wuxian on 16/12/2017.
 */
public class MaximumMatching extends BaseSegmentation {

    public MaximumMatching() {
        this(true);
    }

    public MaximumMatching(boolean b) {
        super(b);

        Dictionary dictionary = new DictionaryTrie();
        dictionary.addAll(WordsLoader.loadWords());
        setDictionary(dictionary);
    }

    @Override
    //影响以下算法的效率的
    //1 @getinterceptLength
    //2 @RecognitionUtil.recog --> 实现的太挫了,给它改改
    public List<String> segImpl(String text) {
        List<String> result = new ArrayList<>();
        final int textLen = text.length();
        int len = getInterceptLength();
        int start = 0;
        while (start < textLen) {
            if (len > textLen - start) {
                len = textLen - start;
            }
            while (!getDictionary().contains(text, start, len) && !RecognitionUtil.recog(text, start, len)) {
                if (len == 1) {
                    break;
                }
                len--;
            }
            addToCuttedList(result, text, start, len);
            start += len;
            len = getInterceptLength();
        }
        return result;
    }
}
