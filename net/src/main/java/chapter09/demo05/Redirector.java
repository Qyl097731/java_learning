package chapter09.demo05;

import chapter09.ThreadPoolUtil;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * HTTP重定向器:从一个Web重定向另外一个网站；
 * 使用命令行读取道德URL和端口号，打开一个Socket，使用302 FOUND编码来接受所有请求到新URL表示的网站
 *
 * @date:2022/10/28 16:32
 * @author: qyl
 */
public class Redirector {
    private static final Logger logger = Logger.getLogger("Redirector");
    private static final ExecutorService pool = ThreadPoolUtil.getExecutorService();

    private final int port;
    private final String newSite;


    public Redirector(String newSite, int port) {
        this.port = port;
        this.newSite = newSite;
    }

    public void start() {
        try {
            ServerSocket server = new ServerSocket(port);
            logger.info("Redirecting connections on port " + server.getLocalPort() + " to " + newSite);

            while (true) {
                try {
                    Socket conn = server.accept();
                    pool.submit(new RedirectHandler(conn));
                } catch (IOException e) {
                    logger.warning("Exception accepting connection");
                } catch (RuntimeException e) {
                    logger.log(Level.SEVERE, "Unexpected error", e);
                }
            }
        } catch (BindException e) {
            logger.log(Level.SEVERE, "Could not start server.", e);
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Error opening server socket", e);
        }
    }


    private class RedirectHandler implements Callable<Void> {

        private final Socket conn;

        private RedirectHandler(Socket conn) {
            this.conn = conn;
        }

        @Override
        public Void call() throws Exception {
            try {
                Writer out = new BufferedWriter(new OutputStreamWriter(conn.getOutputStream(), StandardCharsets.US_ASCII));
                Reader in = new InputStreamReader(new BufferedInputStream(conn.getInputStream()));
                // 只读取第一行，这就是我们需要的全部内容
                StringBuilder request = new StringBuilder(80);
                while (true) {
                    int c = in.read();
                    if (c == '\n' || c == '\r' || c == -1) break;
                    request.append((char) c);
                }

                String get = request.toString();
                // 忽略请求方式
                String[] pieces = get.split("\\w*");
                String theFile = pieces[1];

                // 如果是HTTP1.0或以后版本，发送一个MIME版本
                if (get.indexOf("HTTP") != -1) {
                    out.write("HTTP/1.0 302 FOUND\r\n");
                    Date now = new Date();
                    out.write("Date: " + now + "\r\n");
                    out.write("Server: Redirector 1.1\r\n");
                    out.write("Location: " + newSite + theFile + "\r\n");
                    out.write("Content-type: text/html\r\n\r\n");
                    out.flush();
                }
                // 并不是所有的浏览器都支持重定向，所以我们需要生成HTML指出文档转移到了哪里
                out.write("<HTML><HEAD><TITLE>Document moved</TITLE></HEAD>\r\n");
                out.write("<BODY><H1>Document moved</H1>\r\n");
                out.write("The document " + theFile + " has moved to\r\n<A HREF=\"" + newSite + theFile + "\">" + newSite + theFile + "</A>.\r\n Please update your bookmarks<P>");
                out.write("</BODY></HTML>\r\n");
                out.flush();
                logger.log(Level.INFO, "Redirected " + conn.getRemoteSocketAddress());
            } catch (IOException e) {
                logger.log(Level.WARNING, "Error talking to " + conn.getRemoteSocketAddress(), e);
            }
            return null;
        }
    }

    public static void main(String[] args) {
        int thePort;
        String theSite;
        try {
            theSite = args[0];
            // 删除末尾斜线
            if (theSite.endsWith("/")) {
                theSite = theSite.substring(0, theSite.length() - 1);
            }
        } catch (RuntimeException e) {
            System.out.println("Usage: java Redirecotr http://www.newsite.com/ port");
            return;
        }

        try {
            thePort = Integer.parseInt(args[1]);
        } catch (RuntimeException ex) {
            thePort = 80;
        }

        Redirector redirector = new Redirector(theSite, thePort);
        redirector.start();
    }
}
