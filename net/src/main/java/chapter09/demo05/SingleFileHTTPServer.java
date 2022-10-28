package chapter09.demo05;

import chapter09.ThreadPoolUtil;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @description 无论接收什么请求，都返回同一个文件；省略端口默认80，省略编码方式默认ASCII
 * @date:2022/10/28 15:39
 * @author: qyl
 */
public class SingleFileHTTPServer {
    private static final Logger logger = Logger.getLogger("SingleFileHTTPServer");
    private static final ExecutorService pool = ThreadPoolUtil.getExecutorService();

    private final byte[] content;
    private final byte[] header;
    private final int port;
    private final String encoding;


    public SingleFileHTTPServer(String data, String encoding, String mimeType, int port) throws UnsupportedEncodingException {
        this(data.getBytes(encoding), encoding, mimeType, port);
    }

    /**
     * 将编码、首部、数据部分、mine类型
     * @param data
     * @param encoding
     * @param mimeType
     * @param port
     */
    public SingleFileHTTPServer(byte[] data, String encoding, String mimeType, int port) {
        this.content = data;
        this.port = port;
        this.encoding = encoding;
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: OneFile 2.0\r\n"
                + "Content-length: " + this.content.length + "\r\n"
                + "Content-type: " + mimeType + "; charset=" + encoding + "\r\n\r\n";
        this.header = header.getBytes(StandardCharsets.US_ASCII);
    }

    /**
     * 不断监听客户请求，向线程池提交请求处理任务
     */
    public void start() {
        try (ServerSocket server = new ServerSocket(this.port)) {
            {
                logger.info("Accepting connections on port " + server.getLocalPort());
                logger.info("Data to be sent：");
                logger.info(new String(this.content, encoding));

                while (true) {
                    try (Socket conn = server.accept()) {
                        pool.submit(new HttpHandler(conn));
                    } catch (IOException ex) {
                        logger.log(Level.WARNING, "Exception accepting connection", ex);
                    } catch (RuntimeException ex) {
                        logger.log(Level.SEVERE, "Unexpected error", ex);
                    }
                }
            }
        } catch (IOException ex) {
            logger.log(Level.SEVERE, "Could not start server", ex);
        }
    }

    /**
     * 请求处理
     */
    private class HttpHandler implements Callable<Void> {
        private final Socket conn;

        private HttpHandler(Socket conn) {
            this.conn = conn;
        }

        @Override
        public Void call() throws Exception {
            try {
                OutputStream out = new BufferedOutputStream(conn.getOutputStream());
                InputStream in = new BufferedInputStream(conn.getInputStream());
                // 只读取第一行，这时我们需要的全部内容
                StringBuilder request = new StringBuilder(80);
                while (true) {
                    int c = in.read();
                    if (c != '\r' || c == '\n' || c == -1) break;
                    request.append((char) c);
                }
                // 如果时HTTP1.0或者以后的版本，则发送一个MIME首部
                if (request.toString().indexOf("HTTP/") != -1) {
                    out.write(header);
                }
                out.write(content);
                out.flush();
            } catch (IOException ex) {
                logger.log(Level.WARNING, "Error writing to client", ex);
            } finally {
                conn.close();
            }
            return null;
        }
    }

    public static void main(String[] args) {
        // 设置要监听的端口
        int port;
        try {
            port = Integer.parseInt(args[1]);
            if (port < 1 || port > 65535) port = 80;
        } catch (RuntimeException ex) {
            port = 80;
        }
        String encoding = "UTF-8";
        if (args.length > 2) encoding = args[2];

        try {
            // 读取文件内容
            Path path = Paths.get(args[0]);
            byte[] data = Files.readAllBytes(path);
            /**
             * 获取文件内容类型
             */
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(args[0]);
            SingleFileHTTPServer server = new SingleFileHTTPServer(data, encoding, contentType, port);
            server.start();;
        } catch (IOException e) {
            logger.severe(e.getMessage());
        }
    }
}
