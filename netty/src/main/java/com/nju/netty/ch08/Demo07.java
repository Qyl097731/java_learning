package com.nju.netty.ch08;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.concurrent.Future;

/**
 * @description 优雅地关闭
 * @date:2022/11/22 18:36
 * @author: qyl
 */
public class Demo07 {
    public static void main(String[] args) {
        // 创建处理IO的EventLoopGroup
        EventLoopGroup group = new NioEventLoopGroup ( );
        Bootstrap bootstrap = new Bootstrap ( );
        bootstrap.group (group)
                .channel (NioSocketChannel.class);

        //.... 处理

        // shutdownGracefully 方法将释放所有的资源  同时关闭group 异步操作需要阻塞执行
        Future<?> future = group.shutdownGracefully ();

        // 阻塞执行，直到所有的线程运行完
        future.syncUninterruptibly ();
    }
}
