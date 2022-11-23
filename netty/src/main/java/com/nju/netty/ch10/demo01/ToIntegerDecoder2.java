package com.nju.netty.ch10.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

import java.util.List;

/**
 * @description
 * @date:2022/11/23 20:54
 * @author: qyl
 */
public class ToIntegerDecoder2 extends ReplayingDecoder<Void> {

    // 传入的ByteBuf是ReplayingDecoderByte
    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        // 从入站ByteBuf读取一个int，并加入到解码消息的List
        out.add (in.readInt ());
    }
}
