package com.nju.netty.ch08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description  一个引导服务器
 * @date:2022/11/22 16:57
 * @author: qyl
 */
public class Demo02 {
    public static void main(String[] args) {
        NioEventLoopGroup group = new NioEventLoopGroup ();
        ServerBootstrap bootstrap = new ServerBootstrap ( );
        bootstrap.group (group)
                .channel (NioServerSocketChannel.class)
                .childHandler (new SimpleChannelInboundHandler<ByteBuf> ( ) {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println ("Received data" );
                    }
                });
        ChannelFuture future = bootstrap.bind (new InetSocketAddress (8080));
        future.addListener ((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess ()){
                System.out.println ("server bound" );
            }else {
                System.err.println ("Bound attempt failed" );
                channelFuture.cause ().printStackTrace ();
            }
        });
    }
}
