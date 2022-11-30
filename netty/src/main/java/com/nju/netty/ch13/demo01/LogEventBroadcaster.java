package com.nju.netty.ch13.demo01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import static java.lang.Thread.interrupted;

/**
 * @description 引导服务器
 * @date:2022/11/27 16:26
 * @author: qyl
 */
public class LogEventBroadcaster {
    private final EventLoopGroup group;
    private final Bootstrap bootstrap;
    private final File file;

    public LogEventBroadcaster(InetSocketAddress address, File file) {
        group = new NioEventLoopGroup ( );
        bootstrap = new Bootstrap ( );
        bootstrap.group (group)
                .channel (NioDatagramChannel.class)
                // 设置广播套接字选项
                .option (ChannelOption.SO_BROADCAST, true)
                .handler (new LogEventEncoder (address));
        this.file = file;
    }

    public void run() throws InterruptedException, IOException {
        // 不指定端口 由系统自己分配
        Channel ch = bootstrap.bind (0).sync ().channel ();
        long pointer = 0;
        // 住处理循环
        for (;;){
            long len = file.length ();
            if (len < pointer){
                // file was reset
                pointer = len;
            }else if (len > pointer){
                RandomAccessFile raf = new RandomAccessFile (file, "r");
                // 设置当前文件指针，确保没有任何旧日志被发送
                raf.seek (pointer);
                String line;
                while((line = raf.readLine ()) != null){
                    ch.writeAndFlush (new LogEvent (null,-1,file.getAbsolutePath (),line));
                }
                pointer = raf.getFilePointer ();
                raf.close ();
            }
            try {
                TimeUnit.SECONDS.sleep (1);
            }catch (InterruptedException e){
                // 如果被中断直接退出循环了
                interrupted ();
                break;
            }
        }
    }

    public void stop(){
        group.shutdownGracefully ();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        if (args.length != 2){
            throw new IllegalArgumentException ();
        }

        LogEventBroadcaster broadcaster = new LogEventBroadcaster (new InetSocketAddress ("255.255.255.255"
        ,Integer.parseInt (args[0])),new File (args[1]));

        try {
            broadcaster.run ();
        } finally {
            broadcaster.stop ();
        }

    }
}
