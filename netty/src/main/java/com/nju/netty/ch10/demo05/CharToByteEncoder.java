package com.nju.netty.ch10.demo05;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @description
 * @date:2022/11/23 21:58
 * @author: qyl
 */
public class CharToByteEncoder extends MessageToByteEncoder<Character> {
    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, Character character, ByteBuf byteBuf) throws Exception {
        // 将character 编码成 byte 写出去
        byteBuf.writeChar (character);
    }
}
