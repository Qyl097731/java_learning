package chapter12.demo05;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

/**
 * @date:2022/10/31 8:58
 * @author: qyl
 */
public class FastUDPDiscardServer extends UDPServer{

    public static final int DEFAULT_PORT = 9;

    public FastUDPDiscardServer(){
        super(DEFAULT_PORT);
    }

    public static void main(String[] args) {
        UDPServer server = new FastUDPDiscardServer();
        Thread t = new Thread(server);
        t.start();
    }

    @Override
    protected void respond(DatagramSocket socket, DatagramPacket incoming) throws IOException {
    }
}
