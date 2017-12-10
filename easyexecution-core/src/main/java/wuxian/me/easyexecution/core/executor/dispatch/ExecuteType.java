package wuxian.me.easyexecution.core.executor.dispatch;

/**
 * Created by wuxian on 10/12/2017.
 */
public enum ExecuteType {

    CONCURRENT("concurrent"),
    FIXEDFREQUENTLY("fixedfrequent"),;
    private String type;

    ExecuteType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }
}
