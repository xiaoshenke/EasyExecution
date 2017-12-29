package wuxian.me.easyexecution.biz.word;

import org.apache.log4j.varia.NullAppender;
import org.junit.Before;
import org.junit.Test;
import wuxian.me.easyexecution.biz.word.core.DictionaryTrie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 17/12/2017.
 */
public class MaximumMatchingTest {

    static {
        org.apache.log4j.BasicConfigurator.configure(new NullAppender());
    }

    DictionaryTrie trie = DictionaryTrie.getIns();

    @Before
    public void setup() {
        //trie.add("中国实行的是全国人民当家做主的政治制度");

        trie.add("中国");
        trie.add("实行");
        trie.add("的");
        trie.add("是");
        trie.add("全国");
        trie.add("人民");
        trie.add("当家做主");
        trie.add("政治");
        trie.add("制度");
    }

    String test = "中国实行的是全国人民当家做主的政治制度";

    @Test
    public void noramlSegment() {

        Segmentation seg = new MaximumMatching(true);
        seg.setDictionary(trie);
        System.out.println(seg.seg(test));
    }

    @Test
    public void testExeceedMaxLength() {

        System.out.println(trie.contains("全国人民当家做主"));
        BaseSegmentation seg = new MaximumMatching(true);

        seg.setMaxLength(6);
        trie.add("全国人民当家");  //max length是一个词的时候 才会继续向前+length

        trie.add("全国人民当家做主");

        seg.setDictionary(trie);
        System.out.println(seg.seg(test));
    }

}