package chapter02.demo01;

import jakarta.servlet.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;

/**
 * @description
 * @date:2022/11/3 18:41
 * @author: qyl
 */
public class PrimitiveServlet implements Servlet {
    @Override
    public void init(ServletConfig servletConfig) throws ServletException {
        System.out.println("init");
    }

    @Override
    public ServletConfig getServletConfig() {
        return null;
    }

    @Override
    public void service(ServletRequest servletRequest, ServletResponse servletResponse) throws ServletException, IOException {
        System.out.println("from service");
        PrintWriter output = servletResponse.getWriter();
        String header = "HTTP/1.1 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "\r\n";
        output.println(header);
        output.println("Hello. Roses are red");
        output.println("Violets are blue");
    }

    @Override
    public String getServletInfo() {
        return null;
    }

    @Override
    public void destroy() {

    }
}
