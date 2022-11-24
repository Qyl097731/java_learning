package com.nju.netty.ch11.demo13;

import io.netty.channel.*;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.Serializable;

/**
 * @description
 * @date:2022/11/24 20:53
 * @author: qyl
 */
public class MarshallingInitializer extends ChannelInitializer<Channel> {
    private final MarshallerProvider marshallerProvider;
    private final UnmarshallerProvider unmarshallerProvider;

    public MarshallingInitializer(MarshallerProvider marshallerProvider, UnmarshallerProvider unmarshallerProvider) {
        this.marshallerProvider = marshallerProvider;
        this.unmarshallerProvider = unmarshallerProvider;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (new MarshallingDecoder (unmarshallerProvider));   // 添加Jboss 序列化解码器
        pipeline.addLast (new MarshallingEncoder (marshallerProvider)); // 添加Jboss的序列化编码器
        pipeline.addLast (new ObjectHandler()); // 处理普通的Serializable对象
    }


    private class ObjectHandler extends SimpleChannelInboundHandler<Serializable> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, Serializable msg) throws Exception {
            // 处理序列化数据
        }
    }
}
