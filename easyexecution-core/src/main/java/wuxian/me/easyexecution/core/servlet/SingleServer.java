package wuxian.me.easyexecution.core.servlet;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.servlet.Context;
import org.mortbay.jetty.servlet.ServletHolder;
import wuxian.me.easyexecution.core.executor.JobRunnerManager;

/**
 * Created by wuxian on 10/12/2017.
 */
public class SingleServer {
    public static void main(String[] args) throws Exception {
        new SingleServer().launch();
    }

    private void launch() throws Exception {
        Server server = new Server(8089);
        Context context = new Context(server, "/", Context.SESSIONS);
        context.addServlet(new ServletHolder(new ExecutorServlet()), "/executor");
        ExecutorServer.launch(new ExecutorServer(new JobRunnerManager(), server, context));
    }

}
