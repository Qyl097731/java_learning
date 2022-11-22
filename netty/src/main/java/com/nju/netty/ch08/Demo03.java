package com.nju.netty.ch08;

import io.netty.bootstrap.Bootstrap;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;

/**
 * @description  更好的引导服务器
 * @date:2022/11/22 16:57
 * @author: qyl
 */
public class Demo03 {
    public static void main(String[] args) {
        ServerBootstrap bootstrap = new ServerBootstrap ( );
        bootstrap.group (new NioEventLoopGroup (),new NioEventLoopGroup ())     // 设置提供用来处理事件的EventLoop
                .channel (NioServerSocketChannel.class)     // 指定要使用的Channel实现
                .childHandler (new SimpleChannelInboundHandler<ByteBuf> ( ) {
                    ChannelFuture channelFuture;
                    @Override
                    public void channelActive(ChannelHandlerContext ctx){
                        Bootstrap bootstrap = new Bootstrap ( );// 创建一个Bootstrap实例来来连接远程主机
                        bootstrap.channel (NioSocketChannel.class)
                                .handler (new SimpleChannelInboundHandler<ByteBuf> ( ) { // 为入站请求指定Handler
                                    @Override
                                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) {
                                        System.out.println ("Received data" );
                                    }
                                });
                        bootstrap.group (ctx.channel ().eventLoop ());          // 使用已经分配给子Channel的EventLoop
                        channelFuture = bootstrap.connect (new InetSocketAddress ("www.manning.com",80));
                    }

                    @Override
                    protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg){
                        if (channelFuture.isDone ()){
                            // 连接完成执行其他数据操作
                        }
                    }
                });
        ChannelFuture future = bootstrap.bind (new InetSocketAddress (8080)); // 设置服务器端口
        future.addListener ((ChannelFutureListener) channelFuture -> {
            if (channelFuture.isSuccess ()) {
                System.out.println ("Server bound" );
            }else {
                System.err.println ("Bind attempt failed" );
                channelFuture.cause ().printStackTrace ();
            }
        });
    }
}
