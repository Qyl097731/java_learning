package chapter12.demo03;

import sun.nio.cs.StandardCharsets;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;

/**
 * @date:2022/10/30 23:19
 * @author: qyl
 */
public class UDPDiscardServer {
    public static final int PORT = 9;
    public static final int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        byte[] buffer = new byte[MAX_PACKET_SIZE];
        try (DatagramSocket server = new DatagramSocket(PORT)) {
            DatagramPacket packet = new DatagramPacket(buffer, buffer.length);
            while (true){
                try {
                    server.receive(packet);
                    String s = new String(packet.getData(),0,packet.getLength(), "8859_1");
                    System.out.println(packet.getAddress() + " at port " + packet.getPort() + " says " + s);
                    // 重置下一个数据包的长度
                    packet.setLength(buffer.length);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
    }
}
