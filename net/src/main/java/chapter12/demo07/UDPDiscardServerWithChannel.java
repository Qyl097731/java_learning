package chapter12.demo07;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;

/**
 * @date:2022/10/31 9:29
 * @author: qyl
 */
public class UDPDiscardServerWithChannel {
    public static final int PORT = 9;
    public static final int MAX_PACKET_SIZE = 65507;

    public static void main(String[] args) {
        try {
            DatagramChannel channel = DatagramChannel.open();
            channel.bind(new InetSocketAddress(PORT));
            ByteBuffer buffer = ByteBuffer.allocateDirect(MAX_PACKET_SIZE);
            while (true){
                SocketAddress client = channel.receive(buffer);
                buffer.flip();
                System.out.println(client + " yes ");
                while (buffer.hasRemaining()) System.out.println(buffer.get());
                System.out.println();
                buffer.clear();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
