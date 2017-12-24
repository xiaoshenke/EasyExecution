package wuxian.me.easyexecution.biz.word;


import org.junit.Before;
import org.junit.Test;
import wuxian.me.easyexecution.biz.word.core.Dictionary;
import wuxian.me.easyexecution.biz.word.core.DictionaryTrie;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import static org.junit.Assert.*;

/**
 * @author 杨尚川
 */
public class DictionaryTrieTest {
    private DictionaryTrie trie = null;

    @Before
    public void setUp() {
        trie = DictionaryTrie.getIns();
        trie.add("杨尚川");
        trie.add("杨尚昆");
        trie.add("杨尚喜");
        trie.add("APDPlat");
        trie.add("APP");
        trie.add("APD");

        trie.add("中华人民共和国");
        trie.add("中华");
        trie.add("中心思想");
        trie.add("杨家将");
    }

    @Test
    public void testPrefix() {
        String prefix = "中";
        List<String> result = trie.prefix(prefix);
        assertTrue(result.contains("中心"));
        assertTrue(result.contains("中华"));

        prefix = "中华";
        result = trie.prefix(prefix);
        assertTrue(result.contains("中华人"));

        prefix = "杨";
        result = trie.prefix(prefix);
        assertTrue(result.contains("杨家"));
        assertTrue(result.contains("杨尚"));

        prefix = "杨尚";
        result = trie.prefix(prefix);
        assertTrue(result.contains("杨尚昆"));
        assertTrue(result.contains("杨尚喜"));
        assertTrue(result.contains("杨尚川"));
    }

    @Test
    public void testContains() {
        String item = "杨家将";
        boolean expResult = true;
        boolean result = trie.contains(item);
        assertEquals(expResult, result);

        item = "杨尚川";
        expResult = true;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "中华人民共和国";
        expResult = true;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "APDPlat";
        expResult = true;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "APP";
        expResult = true;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "APD";
        expResult = true;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "杨尚";
        expResult = false;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "杨";
        expResult = false;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "APDP";
        expResult = false;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "A";
        expResult = false;
        result = trie.contains(item);
        assertEquals(expResult, result);

        item = "中华人民";
        expResult = false;
        result = trie.contains(item);
        assertEquals(expResult, result);
    }

    @Test
    public void testWhole() {
        try {
            AtomicInteger h = new AtomicInteger();
            AtomicInteger e = new AtomicInteger();
            List<String> words = Files.readAllLines(Paths.get("src/main/resources/dic.txt"));
            Dictionary dictionary = DictionaryTrie.getIns();
            dictionary.addAll(words);
            words.forEach(word -> {
                for (int j = 0; j < word.length(); j++) {
                    String sw = word.substring(0, j + 1);
                    for (int k = 0; k < sw.length(); k++) {
                        if (dictionary.contains(sw, k, sw.length() - k)) {
                            h.incrementAndGet();
                        } else {
                            e.incrementAndGet();
                        }
                    }
                }
            });
            assertEquals(2599239, e.get());
            assertEquals(1211555, h.get());
        } catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}