package chapter09.demo04;

import java.io.IOException;
import java.net.ServerSocket;

/**
 * @description 检查本地端口占用情况
 * @date:2022/10/28 14:40
 * @author: qyl
 */
public class LocalPortScanner {
    public static void main(String[] args) {
        for (int port = 1; port <= 65535; port++) {
            try {
                ServerSocket server = new ServerSocket(port);
            } catch (IOException e) {
                System.out.println("There is a server on port " + port + ".");
            }
        }
    }
}
