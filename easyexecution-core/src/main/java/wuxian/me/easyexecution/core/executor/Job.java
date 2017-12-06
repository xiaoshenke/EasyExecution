package wuxian.me.easyexecution.core.executor;

import java.util.Properties;

public interface Job {

    String getId();

    void run() throws Exception;

    void cancel() throws Exception;

    double getProgress() throws Exception;

    Properties getProperties();

    boolean isCanceled();

}
