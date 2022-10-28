package chapter10.demo01;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSessionBindingListener;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @description 连接一个安全Http服务器，发送简单的GET请求并显示响应 如果证书过期，可以考虑升级jdk
 * @date:2022/10/28 19:38
 * @author: qyl
 */
public class HttpsClient {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Usage: java HttpsClient2 host");
            return;
        }
        int port = 443; // 默认https端口
        String host = args[0];
        SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
        try (SSLSocket socket = (SSLSocket) factory.createSocket(host, port)) {
            // 启用所有密码组
            String[] supported = socket.getSupportedCipherSuites();
            socket.setEnabledCipherSuites(supported);
            // 写请求
            Writer out = new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8);
            // https在GET行中需要完全URL
            out.write("GET http://" + host + "/ HTTP/1.1\r\n");
            out.write("Host: " + host + "\r\n");
            out.write("\r\n");
            out.flush();

            // 读取响应
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            // 读取首部
            String s;
            while (!(s = in.readLine()).equals("")) {
                System.out.println(s);
            }
            System.out.println();

            // 读取长度
            String contentLength = in.readLine();
            int len = Integer.MAX_VALUE;
            try {
                len = Integer.parseInt(contentLength.trim(), 16);
            } catch (NumberFormatException e) {
                // 这个服务器在响应体的首行没有发送content-length
            }
            System.out.println(contentLength);

            int c, i = 0;
            while ((c = in.read()) != -1 && i++ <len){
                System.out.write(c);
            }
            System.out.println();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
