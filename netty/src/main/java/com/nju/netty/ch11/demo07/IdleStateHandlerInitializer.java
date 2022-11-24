package com.nju.netty.ch11.demo07;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import io.netty.util.CharsetUtil;

import java.util.concurrent.TimeUnit;

/**
 * @description IdleStateHandler触发一个IdleStateEvent事件，HeartBeatHandler重写ChannelInboundHandlerAdapter。
 * 来发送发送心跳，如果已经失活了就关闭并且释放资源
 * @date:2022/11/24 17:02
 * @author: qyl
 */
public class IdleStateHandlerInitializer extends ChannelInitializer<Channel> {
    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (new IdleStateHandler(0,0,60, TimeUnit.SECONDS))
                .addLast (new HeartBeatHandler());  // 将一个心跳处理添加到管道
    }

    public static class HeartBeatHandler extends ChannelInboundHandlerAdapter {
        private static final ByteBuf HEARTBEAT_SEQUENCE = Unpooled
                .unreleasableBuffer (Unpooled.copiedBuffer ("HEARBEAT", CharsetUtil.ISO_8859_1));

        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            if (evt instanceof IdleStateEvent){
                ctx.writeAndFlush (HEARTBEAT_SEQUENCE.duplicate ())
                        .addListener (ChannelFutureListener.CLOSE_ON_FAILURE);
            }else {
                // 如果不是IdleStateEvent就传递给下一个Handler
                super.userEventTriggered (ctx, evt);
            }
        }
    }
}
