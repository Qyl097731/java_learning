package chapter12.demo03;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * @description 从控制哎输入，发送给discard服务器
 * @date:2022/10/30 23:03
 * @author: qyl
 */
public class UDPDiscardClient {
    public static final int PORT = 9;

    public static void main(String[] args) {
        String hostname = args.length > 0 ? args[0] : "localhost";
        try (DatagramSocket socket = new DatagramSocket()) {
            InetAddress host = InetAddress.getByName(hostname);
            BufferedReader userInput = new BufferedReader(new InputStreamReader(System.in));
            while (true) {
                String theLine = userInput.readLine();
                if (theLine.equals(".")) break;
                byte[] data = theLine.getBytes();
                DatagramPacket theOutput = new DatagramPacket(data, data.length, host, PORT);
                socket.send(theOutput);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
