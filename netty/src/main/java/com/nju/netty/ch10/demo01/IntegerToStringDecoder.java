package com.nju.netty.ch10.demo01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @description MessageToMessage 将Integer转换成String
 * @date:2022/11/23 21:04
 * @author: qyl
 */
public class IntegerToStringDecoder  extends MessageToMessageEncoder<Integer> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        // 将Integer消息转换为String表示，并将其添加到输出的List中
        out.add (String.valueOf (msg));
    }
}
