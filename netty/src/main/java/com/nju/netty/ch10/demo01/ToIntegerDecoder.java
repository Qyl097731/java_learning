package com.nju.netty.ch10.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @description
 * @date:2022/11/23 20:39
 * @author: qyl
 */
public class ToIntegerDecoder extends ByteToMessageDecoder {
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while (in.readableBytes ( ) >= 4) {
            out.add (in.readInt ());    // 从入站ByteBuf中读取一个int，并将其添加到解码消息的List中
        }
    }
}
