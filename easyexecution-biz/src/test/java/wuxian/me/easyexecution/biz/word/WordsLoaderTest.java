package wuxian.me.easyexecution.biz.word;

import org.junit.Test;

/**
 * Created by wuxian on 17/12/2017.
 */
public class WordsLoaderTest {

    @Test
    public void testLoad() {
        WordsLoader.loadByPaths("classpath:dic.txt");
    }

}