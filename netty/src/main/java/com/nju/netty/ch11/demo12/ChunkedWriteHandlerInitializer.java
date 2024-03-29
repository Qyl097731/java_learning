package com.nju.netty.ch11.demo12;

import io.netty.channel.*;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslHandler;
import io.netty.handler.stream.ChunkedStream;
import io.netty.handler.stream.ChunkedWriteHandler;
import io.netty.util.concurrent.EventExecutorGroup;

import java.io.File;
import java.io.FileInputStream;

/**
 * @description 直接逐块读取文件并加密向网络写出
 * @date:2022/11/24 20:29
 * @author: qyl
 */
public class ChunkedWriteHandlerInitializer extends ChannelInitializer<Channel> {
    private final File file;
    private final SslContext sslCtx;

    public ChunkedWriteHandlerInitializer(File file, SslContext sslCtx) {
        this.file = file;
        this.sslCtx = sslCtx;
    }

    @Override
    protected void initChannel(Channel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline ( );
        pipeline.addLast (new SslHandler (sslCtx.newEngine (ch.alloc ()))) // 加密数据
                .addLast (new ChunkedWriteHandler ())                       // 处理块数据
                .addLast (new WriteStreamHandler());                        // 写文件数据
    }

    private class WriteStreamHandler extends ChannelInboundHandlerAdapter {
        // 一旦建立连接，该方法就会调用
        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive (ctx);
            ctx.writeAndFlush (new ChunkedStream (new FileInputStream (file)));
        }
    }
}
