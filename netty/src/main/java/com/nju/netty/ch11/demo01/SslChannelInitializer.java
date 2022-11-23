package com.nju.netty.ch11.demo01;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @description 通过ChannelInitializer进行SSLHandler设置
 * @date:2022/11/23 23:26
 * @author: qyl
 */
public class SslChannelInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean startTis;

    public SslChannelInitializer(
            SslContext context, // 传入要使用的SSLContext
            boolean startTis) { // 如果是true，第一个写入的消息不会被加密
        this.context = context;
        this.startTis = startTis;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        SSLEngine sslEngine = context.newEngine (ch.alloc ( ));
        // 将SSLHandler作为第一个ChannelHandler存入pipeline
        ch.pipeline ().addFirst ("ssl",new SslHandler (sslEngine,startTis));
    }
}
