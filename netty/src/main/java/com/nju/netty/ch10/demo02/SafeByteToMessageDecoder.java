package com.nju.netty.ch10.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @description 防止过长的帧的缓存
 * @date:2022/11/23 21:11
 * @author: qyl
 */
public class SafeByteToMessageDecoder extends ByteToMessageDecoder {
    private static final int MAX_FRAME_SIZE = 1024;
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readable = in.readableBytes ();
        if (readable > MAX_FRAME_SIZE) {
            // 检查缓冲区是否超过阈值
            // 超过就跳过这些字节
            in.skipBytes (readable);
            throw new TooLongFrameException ("Frame too big!");
        }
    }
}
