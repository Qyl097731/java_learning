package chapter11.demo05;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @date:2022/10/30 18:40
 * @author: qyl
 */
public class EchoServer {
    public static int DEFAULT_PORT = 7;

    public static void main(String[] args) {
        int port;
        try{
            port = Integer.parseInt(args[0]);
        }catch (RuntimeException ex){
            port = DEFAULT_PORT;
        }

        System.out.println("Listening for connections on port " + port);

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try{
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }
        }

        Set<SelectionKey> readyKeys = selector.selectedKeys();
        Iterator<SelectionKey> iterator = readyKeys.iterator();
        while (iterator.hasNext()){
            SelectionKey key = iterator.next();
            iterator.remove();
            try {
                if (key.isAcceptable()){
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    SocketChannel client = server.accept();
                    System.out.println("Accepted connection from " + client);
                    client.configureBlocking(false);
                    SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE | SelectionKey.OP_READ);
                    // 为了测试 压缩、回绕是否正常执行，应该先进行小的缓冲区进行测试，之后发行之前扩大缓冲区
                    ByteBuffer buffer = ByteBuffer.allocate(100);
                    key2.attach(buffer);
                }else if (key.isWritable()){
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer output = (ByteBuffer)key.attachment();
                    output.flip();
                    client.write(output);
                    output.compact();
                } else if (key.isReadable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer output = (ByteBuffer)key.attachment();
                    client.read(output);
                }
            } catch (IOException e) {
                key.cancel();
                try{
                    key.channel().close();
                } catch (IOException ex) { }
            }
        }

    }
}
