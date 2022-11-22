package com.nju.netty.ch08;


import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelOption;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

/**
 * @description ChannelOption和属性
 * @date:2022/11/22 18:04
 * @author: qyl
 */
public class Demo05 {

    public static void main(String[] args) {
        final AttributeKey<Integer> id = AttributeKey.newInstance ("ID");
        // 创建一个Bootstrap实例来创建客户端Channel并连接他们
        Bootstrap bootstrap = new Bootstrap ( );
        bootstrap.group (new NioEventLoopGroup ( ))  // 设置EventLoopGroup提供了用以处理Channel事件的EventLoop
                .channel (NioSocketChannel.class)       // 设置channel的实现
                .handler (new SimpleChannelInboundHandler<ByteBuf> ( ) {
                    // 设置用以处理Channel的IO和数据的入站
                    @Override
                    public void channelRegistered(ChannelHandlerContext ctx) {
                        Integer idValue = ctx.channel ( ).attr (id).get ( );                // 通过AttributeKey加诺属性以及它的值
                        // 数据处理
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
                        System.out.println ("Received data");
                    }
                });
        // 在Channel创建时绑定Channel属性值
        bootstrap.option (ChannelOption.SO_KEEPALIVE,true)
                .option (ChannelOption.CONNECT_TIMEOUT_MILLIS,5000);
        bootstrap.attr (id,123456); // 存储id属性
        // ֯用配置好的Bootstrap实例连接远程主机
        ChannelFuture future = bootstrap.connect (new InetSocketAddress ("www.manning.com", 80));
        future.syncUninterruptibly ();
    }
}
