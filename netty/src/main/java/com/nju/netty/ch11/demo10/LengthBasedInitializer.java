package com.nju.netty.ch11.demo10;

import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

import javax.swing.plaf.synth.Region;
import java.io.FileInputStream;

/**
 * @description 基于长度进行解码
 * @date:2022/11/24 19:12
 * @author: qyl
 */
public class LengthBasedInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (
                // 起始位置开始前8个字节中存储帧长度
                new LengthFieldBasedFrameDecoder (64 * 1024, 0, 8)
        ).addLast (new FrameHandler ( ));
    }

    private static class FrameHandler extends SimpleChannelInboundHandler<ByteBuf> {

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ByteBuf msg) throws Exception {
            // 梳理帧数据
        }
    }
}
