package wuxian.me.easyexecution.core.util;

/**
 * Interface for listener to get notified before and after a task has been executed.
 *
 * @author hluu
 */
public interface ThreadPoolExecutingListener {

    void beforeExecute(Runnable r);

    void afterExecute(Runnable r);
}
