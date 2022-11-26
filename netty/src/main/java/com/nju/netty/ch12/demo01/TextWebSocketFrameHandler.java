package com.nju.netty.ch12.demo01;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketClientProtocolHandler;

/**
 * @description 负责把信息发送给组内所有的客户端、并且把客户端握手成功后添加到组中
 * @date:2022/11/26 20:29
 * @author: qyl
 */
public class TextWebSocketFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame> {
    private final ChannelGroup group;

    protected TextWebSocketFrameHandler(ChannelGroup group) {
        this.group = group;
    }

    /**
     * 重写用户事件响应逻辑
     *
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == WebSocketClientProtocolHandler.ClientHandshakeStateEvent.HANDSHAKE_COMPLETE) {
            // 握手成功 就删除管道HttpRequest请求处理器
            ctx.pipeline ( ).remove (HttpRequestHandler.class);
            // 通知所有已经连接的WebSocket客户端新的客户端已经加入
            group.writeAndFlush (new TextWebSocketFrame ("Client " + ctx.channel ( ) + " joined"));
            group.add (ctx.channel ( ));
        } else {
            super.userEventTriggered (ctx, evt);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
        // 增加消息的引用计数，并将它写道ChannelGroup中所有已经连接的客户端那边
        group.writeAndFlush (msg.retain ());
    }
}
