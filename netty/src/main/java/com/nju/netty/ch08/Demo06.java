package com.nju.netty.ch08;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.net.InetSocketAddress;

/**
 * @description 引导DatagramChannel
 * @date:2022/11/22 18:21
 * @author: qyl
 */
public class Demo06 {
    public static void main(String[] args) {
        Bootstrap bootstrap = new Bootstrap ( );
        bootstrap.group (new NioEventLoopGroup ( ))
                .channel (NioDatagramChannel.class)
                .handler (new SimpleChannelInboundHandler<ByteBuf> ( ) {
                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        // 数据处理
                    }
                });

        // 无连接
        ChannelFuture future = bootstrap.bind (new InetSocketAddress (0));
        future.addListener ((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess ( )) {
                System.out.println ("channel bound");
            }else {
                System.err.println ("Bind attempt failed" );
            }
        });
    }
}
