package com.nju.netty.ch04.demo01;

import io.netty.util.CharsetUtil;

import java.io.IOException;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @description OIO阻塞式网络编程
 * @date:2022/10/17 21:49
 * @author: qyl
 */
public class PlainOioServer {
    public void serve(int port) throws IOException {
        // 服务器监听某个端口
        try(final ServerSocket socket = new ServerSocket(port)) {
            for (;;){
                // 阻塞时等待连接的建立
                final Socket clientSocket = socket.accept();
                System.out.println("Accepted connection from " + clientSocket);
                new Thread(()->{
                    try(OutputStream out = clientSocket.getOutputStream()) {
                        // 将消息发送给客户端
                        out.write("Hi!\r\n".getBytes(CharsetUtil.UTF_8));
                        out.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }).start();
            }
        }
    }

}
