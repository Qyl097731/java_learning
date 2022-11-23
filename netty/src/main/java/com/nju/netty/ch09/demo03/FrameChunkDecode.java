package com.nju.netty.ch09.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.TooLongFrameException;

import java.util.List;

/**
 * @description
 * @date:2022/11/23 19:02
 * @author: qyl
 */
public class FrameChunkDecode extends ByteToMessageDecoder {
    private final int maxFrameSize;

    // 指定将要产生的帧的最大允许大小
    public FrameChunkDecode(int maxFrameSize) {
        this.maxFrameSize = maxFrameSize;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytes = in.readableBytes ( );
        if (readableBytes > maxFrameSize) {
            // 如果帧太大，则丢弃它并抛出一个TooLongFrameException
            in.clear ( );
            throw new TooLongFrameException ( );
        }
        // 否则就读取该帧，并添加到解码的List中
        ByteBuf buf = in.readBytes (readableBytes);
        out.add (buf);
    }
}
