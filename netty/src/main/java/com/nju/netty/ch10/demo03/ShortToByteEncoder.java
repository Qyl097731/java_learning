package com.nju.netty.ch10.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description 将short转换成byte传输出去
 * @date:2022/11/23 21:19
 * @author: qyl
 */
public class ShortToByteEncoder extends MessageToByteEncoder<Short> {
    @Override
    protected void encode(ChannelHandlerContext cxt, Short msg, ByteBuf out) throws Exception {
        // 将short写入ByteBuf
        out.writeShort (msg);
    }
}
