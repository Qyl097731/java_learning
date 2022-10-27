package chapter08.demo04;

import java.io.IOException;
import java.net.BindException;
import java.net.Socket;

/**
 * @date:2022/10/27 17:09
 * @author: qyl
 */
public class SocketInfo {
    public static void main(String[] args) {
        for (String host : args) {
            try {
                Socket socket = new Socket(host, 80);
                System.out.println("Connected to " + socket.getInetAddress() + " on port " + socket.getPort() + " from" +
                        " port " + socket.getLocalPort() + " of " + socket.getLocalAddress());
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
