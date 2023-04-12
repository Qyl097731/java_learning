package com.nju.concurrent.ch06;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;

/**
 * @description 使用线程池的 Web server
 * @date:2022/12/21 8:51
 * @author: qyl
 */
public class TaskExecutionWebServer {
    private static final int NTHREADS = 100;
    private static final Executor exec = Executors.newFixedThreadPool (NTHREADS);

    public static void main(String[] args) throws IOException {
        ServerSocket server = new ServerSocket (80);
        while (true){
            final Socket conn = server.accept ();
            exec.execute (()->handleRequest(conn));
        }
    }

    private static void handleRequest(Socket conn) {
        System.out.println ("handleRequest");
    }
}
