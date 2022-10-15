package com.nju.netty.ch01.demo1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.LocalDate;

/**
 * @description 基础套接字实现服务器
 * @date:2022/10/15 15:58
 * @author: qyl
 */
public class FirstNetServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(80);
        // 阻塞接收到客户请求
        Socket clientSocket = serverSocket.accept();
        try(BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()))){
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);
            String request , response;
            // 读取客户端发送地数据，直到收到了Done
            while((request = in.readLine()) != null){
                if ("Done".equals(request)){
                    break;
                }
                // 对接收数据地处理
                response = processRequest(request);
                out.println(response);
            }
        }
    }

    private static String processRequest(String request) {
        System.out.println(LocalDate.now() +  "  成功接受数据 ： " + request);
        return LocalDate.now() +  "  成功接受到了数据 ： " + request;
    }
}
