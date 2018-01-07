package wuxian.me.easyexecution.biz.crawler.util;

import com.sun.istack.internal.NotNull;
import org.htmlparser.Node;
import org.htmlparser.util.NodeList;

/**
 * Created by wuxian on 15/4/2017.
 */
public class NodeLogUtil {

    private NodeLogUtil() {
    }

    public static final void printNodeOnly(@NotNull Node node) {
        System.out.println("type: " + node.getClass().getSimpleName());
        System.out.println("getText: " + node.getText());
        System.out.println("toString: " + node.toString());
        System.out.println("toPlainTextString: " + node.toPlainTextString());
    }

    public static final void printChildrenOfNode(@NotNull Node node) {
        NodeList children = node.getChildren();
        if (children == null || children.size() == 0) {
            return;
        }
        for (int i = 0; i < children.size(); i++) {
            Node child = children.elementAt(i);
            printNodeOnly(child);
        }
    }

    //For Log
    public static final void printPreviousBrother(@NotNull Node node) {
        Node real = node.getPreviousSibling();
        while (real != null) {
            System.out.println("type: " + real.getClass().getSimpleName());
            System.out.println("getText: " + real.getText());
            System.out.println("toString: " + real.toString());
            System.out.println("toPlainTextString: " + real.toPlainTextString());

            real = real.getPreviousSibling();
        }
    }

    //For Log
    public static final void printNextBrother(@NotNull Node node) {
        Node real = node.getNextSibling();
        while (real != null) {
            System.out.println("type: " + real.getClass().getSimpleName());
            System.out.println("getText: " + real.getText());
            System.out.println("toString: " + real.toString());
            System.out.println("toPlainTextString: " + real.toPlainTextString());

            real = real.getNextSibling();
        }
    }
}