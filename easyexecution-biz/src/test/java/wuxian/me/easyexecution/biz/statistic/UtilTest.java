package wuxian.me.easyexecution.biz.statistic;

import org.junit.Test;
import wuxian.me.ner.service.statistic.MidStatistics;
import wuxian.me.ner.service.statistic.Util;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by wuxian on 30/12/2017.
 */
public class UtilTest {

    @Test
    public void testSort() {
        Map<MidStatistics.Word, Integer> lengthMap = new HashMap<>(3);
        lengthMap.put(new MidStatistics.Word("wang"), 15);

        lengthMap.put(new MidStatistics.Word("wu"), 3);
        lengthMap.put(new MidStatistics.Word("li"), 22);

        System.out.println(Util.getSortedMap(lengthMap, new Comparator<Integer>() {
            @Override
            public int compare(Integer ovalue1, Integer ovalue2) {
                int ret = ovalue1 < ovalue2 ? 1 : (ovalue1 == ovalue2 ? 0 : -1);
                System.out.println("val1: " + ovalue1 + " val2: " + ovalue2 + " ret: " + ret);
                return ret;
            }
        }));
    }

}