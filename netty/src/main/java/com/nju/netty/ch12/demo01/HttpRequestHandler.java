package com.nju.netty.ch12.demo01;

import io.netty.channel.*;
import io.netty.handler.codec.http.*;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedNioFile;

import java.io.File;
import java.io.RandomAccessFile;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * @description 显示聊天室以及发出的消息的网页
 * @date:2022/11/24 21:45
 * @author: qyl
 */
public class HttpRequestHandler extends SimpleChannelInboundHandler<FullHttpRequest> {

    private final String wsUri;
    private static final File INDEX;

    static {
        URL location = HttpRequestHandler.class.getProtectionDomain ( )
                .getCodeSource ( )
                .getLocation ( );
        try {
            String path = location.toURI ( ) + "index.html";
            path = !path.contains ("file:") ? path : path.substring (5);
            INDEX = new File (path);
        } catch (URISyntaxException e) {
            throw new IllegalStateException ("Unable to local index.html", e);
        }
    }

    public HttpRequestHandler(String wsUri) {
        this.wsUri = wsUri;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, FullHttpRequest msg) throws Exception {
        // 如果请求了WebSocket则协议升级
        if (wsUri.equalsIgnoreCase (msg.uri ())){
            ctx.fireChannelRead (msg.retain ());
        }else {
            // 处理100 Continue请求以符合Http1.1
            if (HttpUtil.is100ContinueExpected(msg)){
                send100Continue(ctx);
            }
            // 读取html文件
            try (RandomAccessFile file = new RandomAccessFile (INDEX, "r")) {
                HttpResponse response = new DefaultHttpResponse (msg.protocolVersion (),HttpResponseStatus.OK);
                response.headers ().set (HttpHeaderNames.CONTENT_TYPE,"text/plain; charset=UTF-8");
                boolean keepAlive = HttpUtil.isKeepAlive (msg);
                // 如果设置了keep-alive，需要添加Http头信息
                if (keepAlive){
                    msg.headers ().set (HttpHeaderNames.CONNECTION,HttpHeaderValues.KEEP_ALIVE)
                            .set (HttpHeaderNames.CONTENT_LENGTH,file.length ());
                }
                // 把响应写回客户端
                ctx.write (response );
                if (ctx.pipeline ().get (SslHandler.class) == null){
                    ctx.write (new DefaultFileRegion (file.getChannel (),0,file.length ()));
                }else {
                    ctx.write (new ChunkedNioFile (file.getChannel ()));
                }
                // 将LastHttpContent写进客户端
                ChannelFuture future = ctx.writeAndFlush (LastHttpContent.EMPTY_LAST_CONTENT);
                if (!keepAlive){
                    // 直接关闭channel
                    future.addListener (ChannelFutureListener.CLOSE);
                }
            }
        }
    }

    private void send100Continue(ChannelHandlerContext ctx) {
        DefaultFullHttpResponse response = new DefaultFullHttpResponse (HttpVersion.HTTP_1_1, HttpResponseStatus.CONTINUE);
        ctx.writeAndFlush (response);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace ();
        ctx.close ();
    }
}
