package chapter02.demo01;

import chapter01.demo01.HttpServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Objects;

/**
 * @description 在chapter01.demo01基础上进行拓展，如果出现了
 * @date:2022/11/3 19:03
 * @author: qyl
 */
public class HttpServer1 {
    private static final String SHUTDOWN_COMMAND = "/SHUTDOWN";
    public static final String CLASS_ROOT =
            Objects.requireNonNull(HttpServer.class.getClassLoader().getResource("")).getPath();
    private boolean shutdown = false;

    public static void main(String[] args) {
        HttpServer1 server = new HttpServer1();
        server.await();
    }

    private void await() {
        int port = 8080;
        while (!shutdown) {
            try (ServerSocket serverSocket = new ServerSocket(port, 1, InetAddress.getByName("127.0.0.1"));
                 Socket socket = serverSocket.accept();
                 InputStream input = socket.getInputStream();
                 OutputStream output = socket.getOutputStream()) {
                Request request = new Request(input);
                request.parse();

                Response response = new Response(output);
                response.setRequest(request);

                if (request.getUri().startsWith("/servlet/")) {
                    ServletProcessor1 processor = new ServletProcessor1();
                    processor.process(request, response);
                } else {
                    StaticResourceProcessor processor = new StaticResourceProcessor();
                    processor.process(request, response);
                }
                shutdown = request.getUri().equals(SHUTDOWN_COMMAND);
            } catch (IOException e) {
                e.printStackTrace();
                System.exit(1);
            }
        }
    }
}
