package wuxian.me.easyexecution.biz.crawler.util;

import com.sun.istack.internal.NotNull;
import com.sun.istack.internal.Nullable;
import org.htmlparser.Node;
import org.htmlparser.NodeFilter;
import org.htmlparser.util.NodeList;
import org.htmlparser.util.ParserException;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by wuxian on 12/7/2017.
 */
public class ParsingUtil {

    private ParsingUtil() {
    }

    private static boolean isSubClass(Class sub, Class superClass) {
        if (superClass == null || sub == null) {
            return false;
        }
        try {
            sub.asSubclass(superClass);
            return true;
        } catch (ClassCastException var4) {
            return false;
        }
    }

    @NotNull
    public static NodeList childrenOfType(@Nullable NodeList list, Class clazz) {
        NodeList nodeList = new NodeList();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Node node = list.elementAt(i);
                if (isSubClass(node.getClass(), clazz)) {

                    nodeList.add(node);
                }
            }
        }
        return nodeList;
    }

    @NotNull
    public static NodeList childrenOfTypeAndContent(@Nullable NodeList list, Class clazz, String content) {
        NodeList nodeList = new NodeList();
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Node node = list.elementAt(i);
                if (isSubClass(node.getClass(), clazz) && node.getText().trim().contains(content)) {
                    nodeList.add(node);
                }
            }
        }
        return nodeList;
    }

    public static Node firstChildOfType(@Nullable NodeList list, Class clazz) {
        return firstChildOfType(list, clazz, false);
    }

    public static Node firstChildOfType(@Nullable NodeList list, Class clazz, boolean recurse) {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Node node = list.elementAt(i);
                if (isSubClass(node.getClass(), clazz)) {
                    return node;
                }
                if (recurse && node.getChildren() != null && node.getChildren().size() != 0) {
                    Node n = firstChildOfType(node.getChildren(), clazz, recurse);
                    if (n != null) {
                        return n;
                    }
                }
            }
        }
        return null;
    }

    public static Node firstChildOfTypeAndContent(@Nullable NodeList list, Class clazz, String content) {
        if (list != null && list.size() != 0) {
            for (int i = 0; i < list.size(); i++) {
                Node node = list.elementAt(i);
                if (isSubClass(node.getClass(), clazz) && node.getText().trim().contains(content)) {
                    return node;
                }
            }
        }
        return null;
    }

    @Nullable
    public static Node firstChild(@Nullable NodeList list) {
        if (list != null && list.size() != 0) {
            return list.elementAt(0);
        }
        return null;
    }

    @Nullable
    public static Node nextBrotherWhoMatch(@Nullable Node current, NodeFilter filter) throws ParserException {

        if (current == null) {
            return null;
        }
        Node next = current.getNextSibling();
        while (next != null) {
            if (filter.accept(next)) {
                return next;
            }
            next = next.getNextSibling();
        }
        return null;
    }

    @Nullable
    public static Node firstChildIfNullThrow(@Nullable NodeList list) throws ParserException {
        Node node = ParsingUtil.firstChild(list);
        if (node == null) {
            throw new ParserException();
        }
        return node;
    }

    public static boolean nodelistEmpty(NodeList list) {
        return list == null || list.size() == 0;
    }

    public static boolean nodelistEmptyIfTrueThrow(NodeList list) throws ParserException {
        boolean b = nodelistEmpty(list);
        if (b) {
            throw new ParserException();
        }
        return b;
    }

    @Nullable
    public static String matchedString(Pattern pattern, String origin) {
        if (origin == null || origin.length() == 0 || pattern == null) {
            return null;
        }

        Matcher matcher = pattern.matcher(origin);
        if (matcher.find()) {
            return matcher.group();
        }
        return null;
    }


    @Nullable
    public static List<String> matchedStrings(Pattern pattern, String origin) {
        if (origin == null || origin.length() == 0 || pattern == null) {
            return null;
        }

        Matcher matcher = pattern.matcher(origin);
        List<String> list = new ArrayList<String>();
        while (matcher.find()) {
            String s = matcher.group();
            if (!list.contains(s)) {
                list.add(s);
                ;
            }
        }

        return list.size() == 0 ? null : list;
    }

    public static Integer matchedInteger(Pattern pattern, String origin) {
        String match = matchedString(pattern, origin);

        return match == null ? null : Integer.parseInt(match);
    }

    public static Long matchedLong(Pattern pattern, String origin) {
        String match = matchedString(pattern, origin);

        return match == null ? null : Long.parseLong(match);
    }

    public static Float matchedFloat(Pattern pattern, String origin) {
        String match = matchedString(pattern, origin);

        return match == null ? null : Float.parseFloat(match);
    }

    public static boolean stringEmptyIfTrueThrow(String s) throws ParserException {
        boolean b = s == null || s.length() == 0;
        if (b) {
            throw new ParserException();
        }
        return b;
    }

    public static boolean containsPattern(Pattern pattern, String content) {
        if (pattern == null || content == null || content.length() == 0) {
            return false;
        }

        Matcher matcher = pattern.matcher(content);
        if (matcher.find()) {
            return true;
        }
        return false;
    }

}