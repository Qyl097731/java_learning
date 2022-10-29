package chapter11.demo01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.SocketChannel;
import java.nio.channels.WritableByteChannel;

/**
 * @date:2022/10/29 21:21
 * @author: qyl
 */
public class ChargenClient {
    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        if (args.length == 0){
            System.out.println("Usage: javaChargenClient host [port]");
            return;
        }

        int port;
        try{
            port = Integer.parseInt(args[1]);
        }catch (RuntimeException e){
            port = DEFAULT_PORT;
        }

        try{
            SocketAddress address = new InetSocketAddress(args[0],port);
            SocketChannel client = SocketChannel.open(address);

            ByteBuffer buffer = ByteBuffer.allocate(74);
            WritableByteChannel out = Channels.newChannel(System.out);
            // 阻塞式读取
//            while(client.read(buffer) !=- 1){
//                buffer.flip();
//                out.write(buffer);
//                buffer.clear();
//            }
            // 非阻塞式读取
            client.configureBlocking(false);
            while (true){
                // 无论读不读到数据都会返回，如果没有读取到，就返回9
                int n = client.read(buffer);
                if (n > 0) {
                    buffer.flip();
                    out.write(buffer);
                    buffer.clear();
                }else if (n == -1){
                    break;
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
