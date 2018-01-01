package wuxian.me.easyexecution.core.servlet;

import com.google.common.base.Preconditions;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

/**
 * Created by wuxian on 10/12/2017.
 */
public class ExecutorServer {

    private JobRunnerManager manager;
    private Server server;
    private Context context;

    public JobRunnerManager getJobRunnerManager() {
        return manager;
    }

    public ExecutorServer(JobRunnerManager jobRunnerManager, Server server, Context context) {
        this.manager = jobRunnerManager;
        this.server = server;
        this.context = context;
    }

    public static void launch(final ExecutorServer azkabanExecutorServer) throws Exception {

        Preconditions.checkNotNull(azkabanExecutorServer);
        azkabanExecutorServer.start();
    }

    private void start() throws Exception {
        this.context.setAttribute(Constants.AZKABAN_SERVLET_CONTEXT_KEY, this);
        try {
            this.server.start();
        } catch (Exception e) {
            ;
        }
    }


}
