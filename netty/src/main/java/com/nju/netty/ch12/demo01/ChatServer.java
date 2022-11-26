package com.nju.netty.ch12.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.ImmediateEventExecutor;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @description 引导服务器
 * @date:2022/11/26 21:19
 * @author: qyl
 */
public class ChatServer {
    private final ChannelGroup channelGroup = new DefaultChannelGroup (ImmediateEventExecutor.INSTANCE);
    private final EventLoopGroup group = new NioEventLoopGroup ( );
    private Channel channel;

    // 引导服务器
    public ChannelFuture start(InetSocketAddress address) {
        ServerBootstrap bootstrap = new ServerBootstrap ( );
        bootstrap.group (group)
                .channel (NioServerSocketChannel.class)
                .childHandler (createInitializer (channelGroup));
        ChannelFuture future = bootstrap.bind (address);
        future.syncUninterruptibly ( );
        channel = future.channel ( );
        return future;
    }

    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new ChatServerInitializer (group);
    }

    public void destroy() {
        if (channel != null) {
            channel.close ( );
        }
        channelGroup.close ( );
        group.shutdownGracefully ( );
    }

    public static void main(String[] args) throws CertificateException, SSLException {
        if (args.length != 1){
            System.out.println ("Please give port as argument" );
            System.exit (1);
        }
        int port = Integer.parseInt (args[0]);
        final ChatServer endpoint = new ChatServer ();
        ChannelFuture future = endpoint.start (new InetSocketAddress (port));
        Runtime.getRuntime ().addShutdownHook (new Thread (endpoint::destroy));
        future.channel ().closeFuture ().syncUninterruptibly ();
    }
}
