package wuxian.me.easyexecution.core.servlet;

import org.codehaus.jackson.map.ObjectMapper;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Created by wuxian on 10/12/2017.
 */
public class ExecutorServlet extends HttpServlet {

    private ExecutorServer application;

    public ExecutorServlet() {
        super();
    }

    @Override
    public void init(final ServletConfig config) throws ServletException {

        System.out.println("executor servlet");
        this.application =
                (ExecutorServer) config.getServletContext().getAttribute(
                        Constants.AZKABAN_SERVLET_CONTEXT_KEY);
    }

    @Override
    public void doGet(final HttpServletRequest req, final HttpServletResponse resp)
            throws ServletException, IOException {

        System.out.println("doGet");
        if (hasParam(req, "hello")) {
            writeJSON(resp, "hello world");
        }
    }

    public boolean hasParam(final HttpServletRequest request, final String param) {
        return request.getParameter(param) != null;
    }

    public static final String JSON_MIME_TYPE = "application/json";

    protected void writeJSON(final HttpServletResponse resp, final Object obj)
            throws IOException {
        resp.setContentType(JSON_MIME_TYPE);
        final ObjectMapper mapper = new ObjectMapper();
        final OutputStream stream = resp.getOutputStream();
        mapper.writeValue(stream, obj);
    }
}

