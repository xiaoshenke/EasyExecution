package wuxian.me.easyexecution.core.executor.id;

import org.omg.CORBA.PUBLIC_MEMBER;

/**
 * Created by wuxian on 9/12/2017.
 */
public class JobIdFactory {

    private static JobIdFactory factory;

    public static JobIdFactory getInstance() {

        if(factory == null) {
            synchronized (JobIdFactory.class) {
                if(factory == null) {
                    factory = new JobIdFactory();
                }
            }
        }
        return factory;
    }

    private JobIdGenerator generator = new DefaultJobIdGenerator();

    public JobIdGenerator getGenerator(){return generator;}
}
