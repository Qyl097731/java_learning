package com.nju.concurrent.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @description 单线程顺序执行
 * @date:2022/12/20 19:33
 * @author: qyl
 */
public class SingleThreadWebServer {
    public static void main(String[] args) throws IOException, InterruptedException {
        ServerSocket serverSocket = new ServerSocket (80);
        while (true) {
            Socket conn = serverSocket.accept ( );
            handleRequest(conn);
        }
    }

    private static void handleRequest(Socket conn) throws InterruptedException {
        TimeUnit.SECONDS.sleep (1);
    }
}
