package chapter09.demo01;

import chapter09.ThreadPoolUtil;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;
import java.util.concurrent.*;

/**
 * @date:2022/10/27 20:57
 * @author: qyl
 */
public class PooledDaytimeServer {

    public final static int PORT = 13;

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
            System.err.println(e);
        }
    }

    private static class DaytimeTask implements Callable<Void> {
        private final Socket conn;

        public DaytimeTask(Socket conn) {
            this.conn = conn;
        }

        @Override
        public Void call() throws Exception {
            try(Writer writer = new OutputStreamWriter(conn.getOutputStream())) {
                Date now = new Date();
                writer.write(now.toString() + "\r\n");
                writer.flush();
            }
            return null;
        }
    }
}
