package com.nju.netty.ch11.demo05;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;

import javax.net.ssl.SSLEngine;

/**
 * @description 启动HTTPs
 * @date:2022/11/24 14:36
 * @author: qyl
 */
public class HttpsCodecInitializer extends ChannelInitializer<Channel> {
    private final SslContext context;
    private final boolean isClient;

    public HttpsCodecInitializer(SslContext context, boolean isClient) {
        this.context = context;
        this.isClient = isClient;
    }


    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        SSLEngine engine = context.newEngine (ch.alloc ( ));
        // 将SslHandler添加到ChannelPipeline中
        pipeline.addLast ("ssl",new SslHandler (engine));
        if (isClient){
            pipeline.addLast ("codec",new HttpClientCodec ());
        }else {
            pipeline.addLast ("codec",new HttpServerCodec ());
        }
    }
}
