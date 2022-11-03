package chapter01.demo01;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.*;
import java.util.Objects;

/**
 * @description HTTP服务器
 * 阻塞的监听指定的应用程序，如果发现接收到请求就解析成Request
 * 如果uri是关闭，就不再循环；否则将对应的uri资源封装成response返回
 *
 * @date:2022/11/3 15:52
 * @author: qyl
 */
public class HttpServer {
    // 指定资源路径
    public static final String WEB_ROOT = Objects.requireNonNull(HttpServer.class.getClassLoader().getResource("webroot")).getPath();
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer server = new HttpServer();
        server.await();
    }

    private void await() {
        int port = 8080;
        try (ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"))){

            while (!shutdown) {
                try (Socket socket = serverSocket.accept()) {
                    // 解析请求
                    InputStream input = socket.getInputStream();
                    Request request = new Request(input);
                    request.parse();
                    // 封装响应
                    OutputStream output = socket.getOutputStream();
                    Response response = new Response(output);
                    response.setRequest(request);
                    // 发送响应
                    response.sendStaticResource();
                    shutdown = request.getUri().endsWith(SHUTDOWN_COMMAND);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }
}
