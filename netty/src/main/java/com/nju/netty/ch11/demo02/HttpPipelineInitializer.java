package com.nju.netty.ch11.demo02;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

/**
 * @description 将HTTP支持添加到应用程序
 * @date:2022/11/23 23:54
 * @author: qyl
 */
public class HttpPipelineInitializer extends ChannelInitializer<Channel> {
    private final boolean client;

    public HttpPipelineInitializer(boolean client) {
        this.client = client;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        // 如果是客户端 需要服务器响应解码器
        if (client){
            pipeline.addLast ("decoder",new HttpResponseDecoder ());
            pipeline.addLast ("encoder",new HttpRequestEncoder ());
        }else {
            pipeline.addLast ("decoder",new HttpRequestDecoder ());
            pipeline.addLast ("encoder",new HttpResponseEncoder ());
        }

    }
}
