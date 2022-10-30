package chapter11.demo06;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.URLConnection;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @description 通过复制，为原始数据通道的缓冲区建立一个副本，将该副本存储为当前通道的附件，这样就可以非阻塞，不需要同步访问源数据通道
 * @date:2022/10/30 18:58
 * @author: qyl
 */
public class NonblockingSingleFileHttpServer {
    private ByteBuffer contentBuffer;
    private int port = 80;

    public NonblockingSingleFileHttpServer(ByteBuffer data, String encoding, String MIMEType, int port) {
        this.port = port;
        String header = "HTTP/1.0 200 OK\r\n"
                + "Server: NonblockingSingleFileHttpServer\r\n"
                + "Content-length: " + data.limit() + "\r\n"
                + "Content-type: " + MIMEType + "\r\n\r\n ";
        byte[] headerData = header.getBytes(StandardCharsets.US_ASCII);

        ByteBuffer buffer = ByteBuffer.allocate(data.limit() + headerData.length);
        buffer.put(headerData);
        buffer.put(data);
        buffer.flip();
        this.contentBuffer = buffer;
    }

    public void run() throws IOException {
        ServerSocketChannel serverChannel = ServerSocketChannel.open();
        serverChannel.bind(new InetSocketAddress(port));
        serverChannel.configureBlocking(false);
        Selector selector = Selector.open();
        serverChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            selector.select();
            Set<SelectionKey> readyKeys = selector.selectedKeys();
            Iterator<SelectionKey> iterator = readyKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    ServerSocketChannel server = (ServerSocketChannel) key.channel();
                    // 接收到连接
                    SocketChannel client = server.accept();
                    client.configureBlocking(false);
                    // 没有绑定缓冲区，等等直接复制缓冲区，共用后备数组，这里也不必浪费空间先分配缓冲区
                    client.register(selector, SelectionKey.OP_READ);
                }else if (key.isReadable()){
                    // 客户端连接先发来请求，服务器能读取该通道中数据了
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = ByteBuffer.allocate(4096);
                    // 读取请求
                    client.read(buffer);
                    // 将通道转换为只写模式 填充完数据，可以写出去数据了
                    key.interestOps(SelectionKey.OP_WRITE);
                    // 复制数据 附加到通道上
                    key.attach(contentBuffer.duplicate());
                } else if (key.isWritable()) {
                    SocketChannel client = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer) key.attachment();
                    // 可能一次写不完 ，之后可以从之前写到的位置继续往后写
                    if (buffer.hasRemaining()){
                        client.write(buffer);
                    }else {
                        client.close();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        if (args.length == 0 ){
            System.out.println("Usage : java NonblockingSingleFileHttpServer file port encoding");
            return;
        }

        try{
            // 要读取的文件
            String contentType = URLConnection.getFileNameMap().getContentTypeFor(args[0]);
            Path file = FileSystems.getDefault().getPath(args[0]);
            byte[] data = Files.readAllBytes(file);
            ByteBuffer input = ByteBuffer.wrap(data);

            int port;
            try{
                port = Integer.parseInt(args[1]);
                if (port < 1|| port > 65535) port = 80;
            }catch (RuntimeException ex){
                port = 80;
            }


            String encoding = "UTF-8";
            if (args.length > 2) encoding = args[2];

            NonblockingSingleFileHttpServer server = new NonblockingSingleFileHttpServer(input, encoding, contentType, port);
            server.run();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
