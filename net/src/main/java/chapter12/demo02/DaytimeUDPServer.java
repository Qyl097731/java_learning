package chapter12.demo02;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @date:2022/10/30 22:06
 * @author: qyl
 */
public class DaytimeUDPServer {
    private static final int PORT = 13;
    private static final Logger audit = Logger.getLogger("requests");
    private static final Logger errors = Logger.getLogger("errors");

    public static void main(String[] args) {
        // 指定监听的端口
        try (DatagramSocket socket = new DatagramSocket(PORT)){
            // 接收UDP请求数据
            DatagramPacket request = new DatagramPacket(new byte[1024],0,1024);
            socket.receive(request);
            // 响应数据
            String daytime = new Date().toString() + "\r\n";
            byte[] data = daytime.getBytes(StandardCharsets.US_ASCII);
            InetAddress host = request.getAddress();
            int port = request.getPort();
            DatagramPacket response = new DatagramPacket(data, data.length, host, port);
            socket.send(response);
            audit.info(daytime + " " + request.getAddress());
        } catch (IOException e) {
            errors.log(Level.SEVERE,e.getMessage(),e);
        }
    }
}
