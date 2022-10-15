package com.nju.netty.ch01.demo1;

import java.io.*;
import java.net.Socket;

/**
 * @description
 * @date:2022/10/15 16:11
 * @author: qyl
 */
public class FirstNetClient {
    public static void main(String[] args) {
        connect("127.0.0.1",80);
    }

    private static void connect(String host, int port) {
        try(Socket socket = new Socket(host,port)){
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
                try (BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()))) {
                    BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));
                    while(true){
                        String input = consoleReader.readLine();

                        writer.write(input + "\n");
                        writer.flush();

                        String msg = null ;
                        if ((msg = reader.readLine()) != null){
                            System.out.println(msg);
                        }
                        if ("Done".equals(input)){
                            break;
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
