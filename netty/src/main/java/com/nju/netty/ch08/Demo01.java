package com.nju.netty.ch08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description 引导一个客户端
 * @date:2022/11/22 16:30
 * @author: qyl
 */
public class Demo01 {
    public static void main(String[] args) {
        EventLoopGroup group = new NioEventLoopGroup ( );
        Bootstrap bootstrap = new Bootstrap ( );
        bootstrap
                .group (group)      // 设置EventLoopGoroup来提供用于处理Channel事件的EventLoop
                .channel (NioSocketChannel.class) // 指定要使用的Channel的实现
                .handler (new SimpleChannelInboundHandler<ByteBuf> ( ) {    // 设置用于Channel事件
                    // 和数据的ChannelInboundHandler
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println ("Received data");
                    }
                });

        // 连接到远程主机
        ChannelFuture future = bootstrap.connect (new InetSocketAddress ("www.manning.com", 80));

        future.addListener ((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess ( )) {
                System.out.println ("Connection established");
            } else {
                System.err.println ("Connection attempt failed");
                channelFuture.cause ( ).printStackTrace ( );
            }
        });

    }
}
