package wuxian.me.easyexecution.core.executor.id;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by wuxian on 9/12/2017.
 */
public class DefaultJobIdGenerator implements JobIdGenerator {

    private AtomicLong id = new AtomicLong(0);

    @Override
    public long generateId() {
        return id.incrementAndGet();
    }
}
