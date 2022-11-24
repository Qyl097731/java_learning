package com.nju.netty.ch11.demo06;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.BinaryWebSocketFrame;
import io.netty.handler.codec.http.websocketx.ContinuationWebSocketFrame;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

/**
 * @description WebSocket服务器：处理协议，升级握手、以及三种类型控制帧的展示
 * @date:2022/11/24 16:42
 * @author: qyl
 */
public class WebSocketServerInitializer extends ChannelInitializer<Channel> {

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ch.pipeline ().addLast (
                new HttpServerCodec (),
                new HttpObjectAggregator (65546),   // 为握手提供聚合的HttpRequest
                new WebSocketServerProtocolHandler ("/websocket"),   // 处理websocket请求，并进行协议升
                // 处理不同的数据帧
                new  TextFrameHandler(),
                new BinaryFrameHandler (),
                new ContinuationFrameHandler ()
        );
    }

    public static final class TextFrameHandler extends SimpleChannelInboundHandler<TextWebSocketFrame>{
        @Override
        protected void channelRead0(ChannelHandlerContext ctx, TextWebSocketFrame msg) throws Exception {
            // handler text frame
        }
    }

    public static final class BinaryFrameHandler extends SimpleChannelInboundHandler<BinaryWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, BinaryWebSocketFrame msg) throws Exception {
            // handler binary frame
        }
    }

    public static final class ContinuationFrameHandler extends SimpleChannelInboundHandler<ContinuationWebSocketFrame>{

        @Override
        protected void channelRead0(ChannelHandlerContext ctx, ContinuationWebSocketFrame msg) throws Exception {
            // handler continuation frame
        }
    }
}
