package com.nju.netty.ch12.demo01;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.stream.ChunkedWriteHandler;

/**
 * @description 将所有需要的Handler注册到Pipiline
 * @date:2022/11/26 20:45
 * @author: qyl
 */
public class ChatServerInitializer extends ChannelInitializer<Channel> {
    private final ChannelGroup group;

    public ChatServerInitializer(ChannelGroup group) {
        this.group = group;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        // 编解码
        pipeline.addLast (new HttpServerCodec ());
        // 文件写入
        pipeline.addLast (new ChunkedWriteHandler ());
        // 将HttpMessage和HttpContent聚合为单个 FullHttpRequest 或FullHttpResponse 。 Pipeline中下一个Channel只会接收到完整的HTTP请求或者响应
        pipeline.addLast (new HttpObjectAggregator (64 * 1024));
        // 处理普通的HttpRequest
        pipeline.addLast (new HttpRequestHandler ("/ws"));
        // WebSocket规范处理器，处理委托管理的WebSocket帧以及握手事件，如果握手成功
        // 会添加所需的ChannelHandler并且删除不再需要的Handler
        pipeline.addLast (new WebSocketServerProtocolHandler ("/ws"));
        pipeline.addLast (new TextWebSocketFrameHandler(group));
    }
}
