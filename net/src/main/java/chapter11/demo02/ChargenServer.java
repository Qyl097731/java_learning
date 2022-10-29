package chapter11.demo02;

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
 * 只用了一个线程来处理服务器接收请求与对现有的连接提供服务，但是多线程，高优先级接收新连接，低优先级对现有的连接提供服务性能更好。
 * @date:2022/10/29 21:36
 * @author: qyl
 */
public class ChargenServer {
    public static int DEFAULT_PORT = 19;

    public static void main(String[] args) {
        int port;
        try {
            port = Integer.parseInt(args[0]);
        } catch (RuntimeException ex) {
            port = DEFAULT_PORT;
        }
        System.out.println("Listening for connections on port " + port);

        // 要发送的数组
        byte[] rotation = new byte[95 * 2];

        for (byte i = ' '; i <= '~'; i++) {
            rotation[i - ' '] = i;
            rotation[i + 95 - ' '] = i;
        }

        ServerSocketChannel serverChannel;
        Selector selector;
        try {
            serverChannel = ServerSocketChannel.open();
            // Java7之前
            // ServerSocket ss = serverChannel.socket();
            // SocketAddress address = new InetSocketAddress(port);
            // ss.bind(address);
            serverChannel.bind(new InetSocketAddress(port));
            serverChannel.configureBlocking(false);
            selector = Selector.open();
            serverChannel.register(selector, SelectionKey.OP_ACCEPT);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        while (true) {
            try {
                selector.select();
            } catch (IOException e) {
                e.printStackTrace();
                break;
            }

            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                iterator.remove();
                try {
                    if (key.isAcceptable()) {
                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
                        SocketChannel client = server.accept();
                        System.out.println("Accepted connection from " + client);
                        client.configureBlocking(false);
                        SelectionKey key2 = client.register(selector, SelectionKey.OP_WRITE);
                        ByteBuffer buffer = ByteBuffer.allocate(74);
                        buffer.put(rotation, 0, 72);
                        buffer.put((byte) '\r');
                        buffer.put((byte) '\n');
                        buffer.flip();
                        key2.attach(buffer);
                    } else if (key.isWritable()) {
                        SocketChannel client = (SocketChannel) key.channel();
                        ByteBuffer buffer = (ByteBuffer) key.attachment();
                        if (!buffer.hasRemaining()){
                            // 重新写入缓冲区 写位置position回到开头，覆盖原内容做准备
                            buffer.rewind();
                            // 得到上一次的首字符 position++
                            int first = buffer.get();
                            // 准备该笔那缓冲区中的数据 写位置position回到开头，覆盖原内容做准备
                            buffer.rewind();
                            // 找寻rotation中的新的首字符位置
                            int position = first - ' ' + 1;
                            // 将数据从rotation复制到缓冲区
                            buffer.put(rotation,position,72);
                            // 在缓冲区末尾存储一个行分隔符
                            buffer.put((byte) '\r');
                            buffer.put((byte) '\n');
                            // 准备缓冲区进行写入
                            buffer.flip();
                        }
                        // 将buffer读出 写到通道
                        client.write(buffer);
                    }
                } catch (Exception e) {
                    key.cancel();
                    try {
                        key.channel().close();
                    } catch (IOException ex) {
                    }
                }
            }
        }
    }
}
