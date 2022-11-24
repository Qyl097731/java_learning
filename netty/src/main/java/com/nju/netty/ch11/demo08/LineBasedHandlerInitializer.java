package com.nju.netty.ch11.demo08;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.util.concurrent.EventExecutorGroup;

/**
 * @description 行尾分割的帧解码器
 * @date:2022/11/24 17:52
 * @author: qyl
 */
public class LineBasedHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (new LineBasedFrameDecoder (60*1024))
                .addLast (new FrameHandler());
    }

    private static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // do something
        }
    }
}
