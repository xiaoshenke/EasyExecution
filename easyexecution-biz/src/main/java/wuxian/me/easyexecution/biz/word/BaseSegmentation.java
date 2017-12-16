package wuxian.me.easyexecution.biz.word;

import wuxian.me.easyexecution.biz.word.Dictionary;
import wuxian.me.easyexecution.biz.word.Segmentation;

import java.util.*;

/**
 * Created by wuxian on 16/12/2017.
 */
public abstract class BaseSegmentation implements Segmentation {

    //protected final Logger LOGGER = LoggerFactory.getLogger(getClass());

    private static final boolean PERSON_NAME_RECOGNIZE = true;// WordConfTools.getBoolean("person.name.recognize", true);
    private static final boolean KEEP_WHITESPACE = false;//WordConfTools.getBoolean("keep.whitespace", false);
    private static final boolean KEEP_CASE = false;//WordConfTools.getBoolean("keep.case", false);
    private static final boolean KEEP_PUNCTUATION = false;//WordConfTools.getBoolean("keep.punctuation", false);
    private static final boolean PARALLEL_SEG = true;//WordConfTools.getBoolean("parallel.seg", true);
    private static final int INTERCEPT_LENGTH = 16;//WordConfTools.getInt("intercept.length", 16);
    private static final String NGRAM = "bigram";// WordConfTools.get("ngram", "bigram");

    //Todo
    //允许动态更改词典操作接口实现
    private static Dictionary dictionary = null;//DictionaryFactory.getDictionary();

    public boolean isParallelSeg() {
        return PARALLEL_SEG;
    }

    public void setDictionary(Dictionary dictionary) {
        this.dictionary.clear();
        this.dictionary = dictionary;

    }

    /**
     * 获取词典操作接口
     *
     * @return 词典操作接口
     */
    public Dictionary getDictionary() {
        return dictionary;
    }

    /**
     * 具体的分词实现，留待子类实现
     *
     * @param text 文本
     * @return 分词结果
     */
    public abstract List<String> segImpl(String text);

    /**
     * 是否启用ngram
     *
     * @return 是或否
     */
    public boolean ngramEnabled() {
        return "bigram".equals(NGRAM) || "trigram".equals(NGRAM);
    }

    /*
    public Map<List<String>, Float> ngram(List<String>... sentences){
        if("bigram".equals(NGRAM)){
            return Bigram.bigram(sentences);

        }
        if("trigram".equals(NGRAM)){
            return Trigram.trigram(sentences);
        }
        return null;
    }
    */

    //分词时截取的字符串的最大长度
    public int getInterceptLength() {
        if (getDictionary().getMaxLength() > INTERCEPT_LENGTH) {
            return getDictionary().getMaxLength();
        }
        return INTERCEPT_LENGTH;
    }

    @Override
    public List<String> seg(String text) {
        List<String> words = segDefault(text);
        //对分词结果进行微调
        //words = WordRefiner.refine(words);
        return words;
    }

    /**
     * 默认分词算法实现：
     * 1、把要分词的文本根据标点符号进行分割
     * 2、对分割后的文本进行分词
     * 3、组合分词结果
     */
    public List<String> segDefault(String text) {
        //Todo:很挫的根据标点符号分词
        List<String> sentences = null;//Punctuation.seg(text, KEEP_PUNCTUATION);


        if (sentences.size() == 1) {
            return segSentence(sentences.get(0));
        }

        //如果是多个句子，可以利用多核提升分词速度
        Map<Integer, String> sentenceMap = new HashMap<>();
        int len = sentences.size();
        for (int i = 0; i < len; i++) {
            //记住句子的先后顺序，因为后面的parallelStream方法不保证顺序
            sentenceMap.put(i, sentences.get(i));
        }
        //用数组收集句子分词结果
        List<String>[] results = new List[sentences.size()];
        sentenceMap.entrySet().stream().forEach(entry -> {
            int index = entry.getKey();
            String sentence = entry.getValue();
            results[index] = segSentence(sentence);
        });
        sentences.clear();
        sentences = null;
        sentenceMap.clear();
        sentenceMap = null;
        List<String> resultList = new ArrayList<>();
        for (List<String> result : results) {
            if (result == null || result.isEmpty()) {
                continue;
            }
            resultList.addAll(result);
        }
        return resultList;
    }

    //将句子切分为词
    private List<String> segSentence(final String sentence) {
        if (sentence.length() == 1) {
            if (KEEP_WHITESPACE) {
                List<String> result = new ArrayList<>(1);
                result.add(new String(KEEP_CASE ? sentence : sentence.toLowerCase()));
                return result;
            } else {
                if (!Character.isWhitespace(sentence.charAt(0))) {
                    List<String> result = new ArrayList<>(1);
                    result.add(new String(KEEP_CASE ? sentence : sentence.toLowerCase()));
                    return result;
                }
            }
        }

        if (sentence.length() > 1) {
            List<String> list = segImpl(sentence);
            if (list != null) {
                if (PERSON_NAME_RECOGNIZE) {  // --> 一个很挫的人名识别
                    //list = PersonName.recognize(list);
                }
                return list;
            } else {
                //LOGGER.error("文本 "+sentence+" 没有获得分词结果");
            }
        }
        return Collections.emptyList();
    }

    /**
     * 将识别出的词放入队列
     *
     * @param result 队列
     * @param text   文本
     * @param start  词开始索引
     * @param len    词长度
     */
    protected void addWord(List<String> result, String text, int start, int len) {
        String String = getWord(text, start, len);
        if (String != null) {
            result.add(String);
        }
    }

    /**
     * 将识别出的词入栈
     *
     * @param result 栈
     * @param text   文本
     * @param start  词开始索引
     * @param len    词长度
     */
    protected void addWord(Stack<String> result, String text, int start, int len) {
        String String = getWord(text, start, len);
        if (String != null) {
            result.push(String);
        }
    }

    /**
     * 获取一个已经识别的词
     *
     * @param text  文本
     * @param start 词开始索引
     * @param len   词长度
     * @return 词或空
     */
    protected String getWord(String text, int start, int len) {
        if (len < 1) {
            return null;
        }
        if (start < 0) {
            return null;
        }
        if (text == null) {
            return null;
        }
        if (start + len > text.length()) {
            return null;
        }
        String wordText = null;
        if (KEEP_CASE) {
            wordText = text.substring(start, start + len);
        } else {
            wordText = text.substring(start, start + len).toLowerCase();
        }
        String String = new String(wordText);
        //方便编译器优化
        if (KEEP_WHITESPACE) {
            //保留空白字符
            return String;
        } else {
            //忽略空白字符
            if (len > 1) {
                //长度大于1，不会是空白字符
                return String;
            } else {
                //长度为1，只要非空白字符
                if (!Character.isWhitespace(text.charAt(start))) {
                    //不是空白字符，保留
                    return String;
                }
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Segmentation englishSegmentation = new BaseSegmentation() {
            @Override
            public List<String> segImpl(String text) {
                List<String> words = new ArrayList<>();
                for (String String : text.split("\\s+")) {
                    words.add(new String(String));
                }
                return words;
            }

            /*
            @Override
            public SegmentationAlgorithm getSegmentationAlgorithm() {
                return null;
            }
            */
        };
        System.out.println(englishSegmentation.seg("i love programming"));
    }

}
