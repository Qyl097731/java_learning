package com.nju.netty.ch11.demo04;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.handler.codec.http.HttpClientCodec;
import io.netty.handler.codec.http.HttpContentCompressor;
import io.netty.handler.codec.http.HttpContentDecompressor;
import io.netty.handler.codec.http.HttpServerCodec;

/**
 * @description 自动压缩的HTTP消息
 * @date:2022/11/24 14:31
 * @author: qyl
 */
public class HttpCompressionInitializer extends ChannelInitializer<Channel> {
    private final boolean isClient;

    public HttpCompressionInitializer(boolean isClient) {
        this.isClient = isClient;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        if (isClient){
            pipeline.addLast ("codec",new HttpClientCodec ());
            // 客户端处理来自服务器的压缩文件
            pipeline.addLast ("decompressor",new HttpContentDecompressor ());
        }else {
            pipeline.addLast ("codec",new HttpServerCodec ());
            // 服务器压缩数据
            pipeline.addLast ("compressor",new HttpContentCompressor ());
        }
    }
}
