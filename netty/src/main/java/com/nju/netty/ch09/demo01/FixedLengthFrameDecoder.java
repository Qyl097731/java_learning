package com.nju.netty.ch09.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description 生成固定3个字节数的解码器
 * @date:2022/11/23 15:05
 * @author: qyl
 */
public class FixedLengthFrameDecoder extends ByteToMessageDecoder {
    private final int frameLength;

    public FixedLengthFrameDecoder(int frameLength) { // 指定要生成的帧的长度
        if (frameLength < 0) {
            throw new IllegalArgumentException ("frameLength must be a positive integer : " + frameLength);
        }
        this.frameLength = frameLength;
    }

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        // 检查是否有足够的字节数
        while (byteBuf.readableBytes ( ) >= frameLength) {
            // 读取一个帧
            ByteBuf buf = byteBuf.readBytes (frameLength);
            // 将帧添加到已解码的消息队列中
            out.add (buf);
        }
    }
}
