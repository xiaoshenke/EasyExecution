package wuxian.me.easyexecution.biz;

import org.apache.log4j.Logger;
import org.junit.Test;

import static org.junit.Assert.*;

public class GlobalTest {
    @Test
    public void testGlobal() {
        Global.init();
        Logger logger = Logger.getLogger(GlobalTest.class);
        logger.error("hello");
    }

}