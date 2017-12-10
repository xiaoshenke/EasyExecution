package wuxian.me.easyexecution.core.executor.dispatch;

/**
 * Created by wuxian on 10/12/2017.
 */
public interface IDispatcher {

    void submit(Runnable runnable);

    void cancelByExecId(String execId);
}
