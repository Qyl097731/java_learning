package chapter12.demo05;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @date:2022/10/31 9:00
 * @author: qyl
 */
public class UDPEchoServer extends UDPServer{
    public static final int DEFAULT_PORT = 7;

    public UDPEchoServer() {
        super(DEFAULT_PORT);
    }


    @Override
    protected void respond(DatagramSocket socket, DatagramPacket packet) throws IOException {
        DatagramPacket outcoming = new DatagramPacket(packet.getData(),packet.getLength(),packet.getAddress(),
                packet.getPort());
        socket.send(outcoming);
    }

    public static void main(String[] args) {
        UDPServer server = new UDPEchoServer();
        Thread t = new Thread(server);
        t.start();
    }
}
