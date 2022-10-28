package chapter09.demo03;

import chapter09.ThreadPoolUtil;
import chapter09.demo01.PooledDaytimeServer;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description
 * @date:2022/10/28 13:33
 * @author: qyl
 */
public class LoggingDaytimeServer {

    public final static int PORT = 13;
    private final static Logger auditLogger = Logger.getLogger("requests");
    private final static Logger errorLogger = Logger.getLogger("errors");

    public static void main(String[] args) {
        ExecutorService pool = ThreadPoolUtil.getExecutorService();
        try (ServerSocket server = new ServerSocket(PORT)) {
            // 持续监听
            while (true) {
                Socket conn = server.accept();
                // 创建任务
                Callable<Void> task = new DaytimeTask(conn);
                // 像线程池提交任务
                pool.submit(task);
            }
        } catch (IOException e) {
            errorLogger.log(Level.SEVERE, "Couldn't start server", e);
        } catch (RuntimeException ex) {
            errorLogger.log(Level.SEVERE, "unexpected error " + ex.getMessage(), ex);
        }
    }

    private static class DaytimeTask implements Callable<Void> {
        private final Socket conn;

        public DaytimeTask(Socket conn) {
            this.conn = conn;
        }

        @Override
        public Void call() throws Exception {
            try (Writer writer = new OutputStreamWriter(conn.getOutputStream())) {
                Date now = new Date();
                auditLogger.info(now + " " + conn.getRemoteSocketAddress());
                writer.write(now.toString() + "\r\n");
                writer.flush();
            }
            return null;
        }
    }
}

