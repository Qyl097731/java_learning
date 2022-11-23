package com.nju.netty.ch10.demo05;

import io.netty.channel.CombinedChannelDuplexHandler;

/**
 * @description
 * @date:2022/11/23 22:01
 * @author: qyl
 */
public class CombinedByteCharCodec extends CombinedChannelDuplexHandler<ByteToCharDecoder,CharToByteEncoder> {

    public CombinedByteCharCodec() {
    }

    public CombinedByteCharCodec(ByteToCharDecoder inboundHandler, CharToByteEncoder outboundHandler) {
        // 将委托交给父类
        super (inboundHandler, outboundHandler);
    }
}


