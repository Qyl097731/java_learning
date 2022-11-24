package com.nju.netty.ch11.demo03;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description 自动觉和HTTP的消息
 * @date:2022/11/24 14:24
 * @author: qyl
 */
public class HttpAggregatorInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;


    public HttpAggregatorInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        if (isClient){
            pipeline.addLast ("codec",new HttpClientCodec ());
        }else{
            pipeline.addLast ("codec",new HttpServerCodec ());
        }
        // 设置聚合器，且最大消息大小为512KB
        pipeline.addLast ("aggregator",new HttpObjectAggregator (512*1024));

    }
}
