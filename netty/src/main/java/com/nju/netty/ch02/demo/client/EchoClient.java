package com.nju.netty.ch02.demo.client;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description 引导客户端
 * @date:2022/10/15 20:26
 * @author: qyl
 */
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            // 指定EventLoopGroup以处理客户端时间，需要适用于NIO实现
            b.group(group)
                    // 适用于NIO传输的Channel类型
                    .channel(NioSocketChannel.class)
                    // 设置服务器的IP 端口
                    .remoteAddress(new InetSocketAddress(host, port))
                    // 当创建Channle时 向pipeline中增加处理器
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(new EchoClientHandler());
                        }
                    });
            // 连接到远程节点，阻塞等待直到连接完成
            ChannelFuture f = b.connect().sync();
            //阻塞直到关闭Channel
            f.channel().closeFuture().sync();
        } finally {
            // 关闭线程池并且释放所有资源
            group.shutdownGracefully().sync();
        }

    }

    public static void main(String[] args) throws Exception {
        if (args.length != 2) {
            System.err.println("Usage: "+ EchoClient.class.getSimpleName() + "<host><port>");
            return;
        }

        String host = args[0];
        int port = Integer.parseInt(args[1]);
        new EchoClient(host,port).start();;
    }
}
