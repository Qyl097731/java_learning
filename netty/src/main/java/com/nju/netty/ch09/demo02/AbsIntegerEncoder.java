package com.nju.netty.ch09.demo02;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @description 消息编码器 将输入读取一个整数并以绝对值返回
 * @date:2022/11/23 15:49
 * @author: qyl
 */
public class AbsIntegerEncoder extends MessageToMessageEncoder<ByteBuf> {

    @Override
    protected void encode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        while(in.readableBytes () == 4){
            int value = Math.abs (in.readInt ());
            out.add (value);
        }
    }
}
