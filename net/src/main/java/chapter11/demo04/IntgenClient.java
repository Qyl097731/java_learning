package chapter11.demo04;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.SocketChannel;

/**
 * @date:2022/10/30 18:24
 * @author: qyl
 */
public class IntgenClient {
    public static int DEFAULT_PORT = 1919;

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Usage: Java IntgenClient host [port]");
            return;
        }

        int port ;
        try {
            port = Integer.parseInt(args[0]);
        }catch (RuntimeException e){
            port = DEFAULT_PORT;
        }

        try {
            SocketChannel client = SocketChannel.open(new InetSocketAddress(args[0],port));
            ByteBuffer buffer = ByteBuffer.allocate(4);
            IntBuffer view = buffer.asIntBuffer();

            for (int expected = 0; ; expected++) {
                client.read(buffer);
                int actual = view.get();
                buffer.clear();
                view.rewind();

                if (actual != expected){
                    System.out.println("Expected " + expected + "; was " +actual);
                    break;
                }
                System.out.println(actual);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
