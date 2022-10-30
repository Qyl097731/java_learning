package chapter12.demo01;

import java.io.IOException;
import java.net.*;
import java.nio.charset.StandardCharsets;

/**
 * @description
 * @date:2022/10/30 21:32
 * @author: qyl
 */
public class DaytimeUDPClient {
    private final static int PORt = 13;
    private static final String HOSTNAME = "time.nist.gov";

    public static void main(String[] args) {
        try (DatagramSocket socket = new DatagramSocket(0)) {
            socket.setSoTimeout(10000);
            InetAddress host = InetAddress.getByName(HOSTNAME);
            DatagramPacket request = new DatagramPacket(new byte[1], 1, host, 13);
            byte[] data = new byte[1024];
            DatagramPacket response = new DatagramPacket(data, data.length);
            socket.send(request);
            socket.receive(response);
            String result = new String(response.getData(), 0, response.getLength(), StandardCharsets.US_ASCII);
            System.out.println(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
