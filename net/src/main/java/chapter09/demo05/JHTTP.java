package chapter09.demo05;

import chapter09.ThreadPoolUtil;

import java.io.File;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description 不在执行主线程中处理到达的每一个请求，将入站链接放入一个池，由RequestProcessor类的不同实例从池中删除连接并进行处理。
 * @date:2022/10/28 17:42
 * @author: qyl
 */
public class JHTTP {
    private static final Logger logger = Logger.getLogger(JHTTP.class.getCanonicalName());
    private static final ExecutorService POOL = ThreadPoolUtil.getExecutorService();
    private static final String INDEX_FILE = "index.html";

    private final File rootDirectory;
    private final int port;

    public JHTTP(File rootDirectory, int port) throws IOException {
        if (!rootDirectory.isDirectory()) {
            throw new IOException(rootDirectory + " does not exist as a directory");
        }
        this.rootDirectory = rootDirectory;
        this.port = port;
    }

    public void start() throws IOException {
        try (ServerSocket server = new ServerSocket(port)) {
            logger.info("Accepting connections on port " + server.getLocalPort());
            logger.info("Document Root " + rootDirectory);

            /**
             * 反复接收数据并提交给线程池，每个实例都由RequestProcessor的run处理
             */
            while (true) {
                try {
                    Socket request = server.accept();
                    Runnable r = new RequestProcessor(rootDirectory, INDEX_FILE, request);
                    POOL.submit(r);
                } catch (IOException e) {
                    logger.log(Level.WARNING, "Error accepting connection", e);
                }
            }
        }
    }

    public static void main(String[] args) {
        // 得到文档跟
        File docroot;
        try {
            docroot = new File(args[0]);
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Usage: java JHTTP docroot port");
            return;
        }

        // 设置成要监听的端口
        int port;
        try{
            port = Integer.parseInt(args[1]);
            if (port < 0 || port > 65535) port = 80;
        }catch (RuntimeException e){
            port = 80;
        }
        try{
            JHTTP webserver = new JHTTP(docroot, port);
            webserver.start();
        } catch (IOException e) {
            logger.log(Level.SEVERE,"Server could not start",e);
        }
    }
}
