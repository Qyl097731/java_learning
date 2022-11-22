package com.nju.netty.ch08;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;

import java.net.InetSocketAddress;

/**
 * @description ChannelInitializer
 * @date:2022/11/22 17:41
 * @author: qyl
 */
public class Demo04 {
    public static void main(String[] args) throws InterruptedException {
        ServerBootstrap bootstrap = new ServerBootstrap ()  ;
        bootstrap.group (new NioEventLoopGroup (),new NioEventLoopGroup ())
                .childHandler (new ChannelInitializerImpl());
        ChannelFuture future = bootstrap.bind (new InetSocketAddress (8080));
        future.sync ();
    }

    static final class ChannelInitializerImpl extends ChannelInitializer{

        @Override
        protected void initChannel(Channel ch){
            ChannelPipeline pipeline = ch.pipeline ( );
            pipeline.addLast (new HttpClientCodec ());
            pipeline.addLast (new HttpObjectAggregator (Integer.MAX_VALUE));
        }
    }
}
