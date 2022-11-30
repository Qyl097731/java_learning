package com.nju.netty.ch13.demo01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;
import io.netty.handler.logging.LoggingHandler;

import java.net.InetSocketAddress;

/**
 * @description Decoder和handler注册到ChannelPipeline中
 * @date:2022/11/30 20:41
 * @author: qyl
 */
public class LogEventMonitor {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;

    public LogEventMonitor(InetSocketAddress address) {
        group = new NioEventLoopGroup ( );
        bootstrap = new Bootstrap ( );
        bootstrap.group (group)
                .channel (NioDatagramChannel.class)
                .handler (new ChannelInitializer<Channel> ( ) {
                    @Override
                    protected void initChannel(Channel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline ( );
                        pipeline.addLast (new LogEventDecoder ( ));
                        pipeline.addLast (new LoggingHandler ( ));
                    }
                }).localAddress (address);
    }

    public Channel bind() {
        return bootstrap.bind ( ).syncUninterruptibly ( ).channel ( );
    }

    public void stop() {
        group.shutdownGracefully ( );
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            throw new IllegalArgumentException ("Usage : LogEventMonitor <port>");
        }

        LogEventMonitor monitor = new LogEventMonitor (new InetSocketAddress (Integer.parseInt (args[0])));
        try {
            Channel channel = monitor.bind ( );
            System.out.println ("LogEventMonitor running");
            channel.closeFuture ().sync ();
        } catch (InterruptedException e) {
            e.printStackTrace ( );
        } finally {
            monitor.stop ( );
        }

    }
}
