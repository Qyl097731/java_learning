package com.nju.netty.ch11.demo14;

import com.google.protobuf.MessageLite;
import io.netty.channel.*;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;

/**
 * @date:2022/11/24 21:14
 * @author: qyl
 */
public class ProtoBufInitializer extends ChannelInitializer<Channel> {
    private final MessageLite lite;

    public ProtoBufInitializer(MessageLite lite) {
        this.lite = lite;
    }

    @Override
    protected void initChannel(Channel ch){
        ChannelPipeline pipeline = ch.pipeline ();
        pipeline.addLast (new ProtobufVarint32FrameDecoder ());     // 添加动态分割解码器
        pipeline.addLast (new ProtobufEncoder ());                  // 添加编码器

        pipeline.addLast (new ProtobufDecoder (lite));              // 解码器
        pipeline.addLast (new ObjectHandler());                     // 添加ObjectHandler 来处理普通序列化对象
    }

    private class ObjectHandler extends SimpleChannelInboundHandler<Object> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
            // Do something with the object
        }
    }
}
