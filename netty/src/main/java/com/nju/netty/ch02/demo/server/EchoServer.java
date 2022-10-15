package com.nju.netty.ch02.demo.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description 引导服务器
 * @date:2022/10/15 19:46
 * @author: qyl
 */
public class EchoServer {
    private final int port;

    public EchoServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) throws InterruptedException {
        if (args.length != 1) {
            System.err.println("Usage: " + EchoServer.class.getSimpleName() + " ");
        }
        // 设置端口
        int port = Integer.parseInt(args[0]);
        new EchoServer(port).start();
    }

    private void start() throws InterruptedException {
        final EchoServerHandler handler = new EchoServerHandler();
        // 创建Event-LoopGroup
        NioEventLoopGroup group = new NioEventLoopGroup();
        // 创建Server-BootStrap
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(group)
                    // 指定所使用的NIO传输Channel
                    .channel(NioServerSocketChannel.class)
                    // 使用指定的端口设置套接字地址
                    .localAddress(new InetSocketAddress(port))
                    .childHandler(new ChannelInitializer() {
                        @Override
                        protected void initChannel(Channel channel) throws Exception {
                            //添加一个EchoServerHandler 到 子Channel的ChannelPipeline
                            channel.pipeline().addLast(handler);
                            // EchoServerHandler被标注为@Sharebale 所以我们总是使用相同实例
                        }
                    });
            // 异地绑定服务器
            ChannelFuture f = b.bind().sync();
            // 获取Channel的CloseFeature，并且阻塞当前线程直到他完成
            f.channel().closeFuture().sync();
        }finally {
            group.shutdownGracefully().sync();
        }
    }
}
