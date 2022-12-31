package com.nju.concurrent.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Time;
import java.util.concurrent.TimeUnit;

/**
 * @description 为每个请求启动一个线程
 * @date:2022/12/20 19:35
 * @author: qyl
 */
public class ThreadPerTaskWebServer {
    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket (80);
        while (true){
            Socket con = server.accept ( );
            new Thread (()-> handlerRequest(con)).start ();
        }
    }

    private static void handlerRequest(Socket con) {
        try {
            TimeUnit.SECONDS.sleep (1);
            System.out.println ("为每个请求启动一个线程");
        } catch (InterruptedException e) {
            throw new RuntimeException (e);
        }
    }
}
