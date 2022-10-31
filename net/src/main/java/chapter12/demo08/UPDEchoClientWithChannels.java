package chapter12.demo08;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.util.Iterator;
import java.util.Set;

/**
 * @date:2022/10/31 9:47
 * @author: qyl
 */
public class UPDEchoClientWithChannels {
    public static final int PORT = 7;
    private static final int LIMIT = 100;

    public static void main(String[] args) {
        SocketAddress remote;
        try {
            remote = new InetSocketAddress(args[0], PORT);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return;
        }

        try (DatagramChannel channel = DatagramChannel.open()) {
            channel.configureBlocking(false);
            // 只作用域某特定主机的收发消息
            channel.connect(remote);
            Selector selector = Selector.open();
            channel.register(selector, SelectionKey.OP_READ | SelectionKey.OP_WRITE);

            ByteBuffer buffer = ByteBuffer.allocate(4);
            int n = 0;
            int numbersRead = 0;
            while (true) {
                if (numbersRead == LIMIT) break;
                // 为一个连接等待一分钟
                selector.select(60000);
                Set<SelectionKey> readyKeys = selector.selectedKeys();
                if (readyKeys.isEmpty() && n == LIMIT){
                    // 所有包已经写入，不会有更多数据从网络送达。
                    break;
                }else{
                    Iterator<SelectionKey> iterator = readyKeys.iterator();
                    while (iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        iterator.remove();
                        if (key.isReadable()){
                            buffer.clear();
                            channel.read(buffer);
                            buffer.flip();
                            int echo = buffer.getInt();
                            System.out.println("Read : " + echo);
                            numbersRead++;
                        }
                        if (key.isWritable()){
                            buffer.clear();
                            buffer.putInt(n);
                            buffer.flip();
                            channel.write(buffer);
                            System.out.println("Wrote: "+ n);
                            n++;
                            if (n == LIMIT) {
                                // 全部写入
                                key.interestOps(SelectionKey.OP_READ);
                            }
                        }
                    }
                }
            }
            System.out.println("Echoed " + numbersRead + " out of " + LIMIT + " sent");
            System.out.println("Success rate: " + 100.0 * numbersRead / LIMIT + "%");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
