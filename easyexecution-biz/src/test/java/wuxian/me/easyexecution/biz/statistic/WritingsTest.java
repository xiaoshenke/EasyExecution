package wuxian.me.easyexecution.biz.statistic;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 30/12/2017.
 */
public class WritingsTest {

    Writings writings;

    @Before
    public void setup() {
        writings = new Writings();
        List<String> baseWordList = new ArrayList<>();
        baseWordList.add("今年");
        baseWordList.add("马上");
        baseWordList.add("就");
        baseWordList.add("要");
        baseWordList.add("结束");
        baseWordList.add("明年");
        baseWordList.add("马上");
        baseWordList.add("就");
        baseWordList.add("来了");

        writings.setBaseWordList(baseWordList);  //这里应该由分词程序切好词 然后set一下
    }

    @Test
    public void testWritings() {
        writings.generateWordsMap(writings);
        System.out.println(writings.getWordPostionMap());
    }

    @Test
    public void testSegAndSort() {
        writings.generateWordsMap(writings);
        System.out.println(Util.getSortedMap(writings.getWordPostionMap(), new Comparator<List<Integer>>() {
            @Override
            public int compare(List<Integer> o1, List<Integer> o2) {
                int ret = o1.size() < o2.size() ? 1 : (o1.size() == o2.size() ? 0 : -1);
                return ret;
            }
        }));
    }

}