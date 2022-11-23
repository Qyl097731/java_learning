package com.nju.netty.ch10.demo03;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @description 消息从Integer转换成String 传到 下一个Channel
 * @date:2022/11/23 21:25
 * @author: qyl
 */
public class IntegerToStringEncoder extends MessageToMessageEncoder<Integer> {

    @Override
    protected void encode(ChannelHandlerContext ctx, Integer msg, List<Object> out) throws Exception {
        out.add (String.valueOf (msg));
    }
}
