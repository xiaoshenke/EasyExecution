package wuxian.me.easyexecution.biz;

import com.google.common.reflect.ClassPath;
import org.apache.log4j.Logger;
import org.junit.Test;
import wuxian.me.easyexecution.biz.crawler.util.FileUtil;

import java.io.File;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import static org.junit.Assert.*;

public class GlobalTest {
    @Test
    public void testGlobal() {
        Global.init();
        Logger logger = Logger.getLogger(GlobalTest.class);
        logger.error("hello");
    }

    @Test
    public void testJarFile() throws Exception {
        File file = new File("/Users/dashu/Desktop/Segmentation-1.0-jar-with-dependencies.jar");
        JarFile jarFile = new JarFile(file);

        Enumeration<JarEntry> entries = jarFile.entries();
        while (entries.hasMoreElements()) {
            JarEntry entry = entries.nextElement();
            if (entry.isDirectory() || entry.getName().equals(JarFile.MANIFEST_NAME)) {
                continue;
            }
            System.out.println(entry.getName());
        }

    }

}