package com.nju.netty.ch10.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageCodec;
import io.netty.handler.codec.http.websocketx.*;

import java.util.List;

/**
 * @description 处理INBOUND_IN为WebSocketFrame的数据，以及OUTBOUND_IN的MyWebSocketFrame
 * @date:2022/11/23 21:38
 * @author: qyl
 */
public class WebSocketConvertHandler extends MessageToMessageCodec<WebSocketFrame,
        WebSocketConvertHandler.MyWebSocketFrame> {

    /**
     * 将myWebSocketFrame编码为WebSocketFrame
     * @throws Exception
     */
    @Override
    protected void encode(ChannelHandlerContext ctx, MyWebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.getData ( ).duplicate ( ).retain ( );
        switch (msg.getType()) { //实例化一个指定子类型的WebSocketFrame
            case BINARY:
                out.add(new BinaryWebSocketFrame(payload));
                break;
            case TEXT:
                out.add(new TextWebSocketFrame(payload));
                break;
            case CLOSE:
                out.add(new CloseWebSocketFrame(true, 0, payload));
                break;
            case CONTINUATION:
                out.add(new ContinuationWebSocketFrame(payload));
                break;
            case PONG:
                out.add(new PongWebSocketFrame(payload));
                break;
            case PING:
                out.add(new PingWebSocketFrame(payload));
                break;
            default:
                throw new IllegalStateException(
                        "Unsupported websocket msg " + msg);
        }

    }

    /**
     * 将webSocketFrame解码为myWebSocketFrame
     * @throws Exception
     */
    @Override
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        ByteBuf payload = msg.content ( ).retain ( );
        if (msg instanceof BinaryWebSocketFrame){
            out.add (new MyWebSocketFrame (MyWebSocketFrame.FrameType.BINARY, payload));
        }else if (msg instanceof CloseWebSocketFrame) {
            out.add (new MyWebSocketFrame (MyWebSocketFrame.FrameType.CLOSE, payload));
        }else if (msg instanceof PingWebSocketFrame) {
                out.add(new MyWebSocketFrame (
                        MyWebSocketFrame.FrameType.PING, payload));
        } else if (msg instanceof PongWebSocketFrame) {
                out.add(new MyWebSocketFrame (
                        MyWebSocketFrame.FrameType.PONG, payload));
        } else if (msg instanceof TextWebSocketFrame) {
                out.add(new MyWebSocketFrame (
                        MyWebSocketFrame.FrameType.TEXT, payload));
        } else if (msg instanceof ContinuationWebSocketFrame) {
                out.add(new MyWebSocketFrame (
                        MyWebSocketFrame.FrameType.CONTINUATION, payload));
        } else {
            throw new IllegalStateException("Unsupported websocket msg " + msg);
        }
    }

    /**
     * OUTBOUND_IN类型
     */
    static class MyWebSocketFrame{
        /**
         * 定义被包装的有效负载的WebSocketFrame的类型
         */
        public enum FrameType{
            BINARY,CLOSE,PING,PONG,TEXT,CONTINUATION
        }
        private final FrameType type;
        private final ByteBuf data;

        MyWebSocketFrame(FrameType type, ByteBuf data) {
            this.type = type;
            this.data = data;
        }

        public FrameType getType() {
            return type;
        }

        public ByteBuf getData() {
            return data;
        }
    }
}
