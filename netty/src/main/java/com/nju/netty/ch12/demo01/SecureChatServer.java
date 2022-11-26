package com.nju.netty.ch12.demo01;


import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.group.ChannelGroup;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;

import javax.net.ssl.SSLException;
import java.net.InetSocketAddress;
import java.security.cert.CertificateException;

/**
 * @description 引导服务器
 * @author: qyl
 */
public class SecureChatServer extends ChatServer {
    private final SslContext context;

    public SecureChatServer(SslContext context) {
        this.context = context;
    }

    @Override
    protected ChannelInitializer<Channel> createInitializer(ChannelGroup group) {
        return new SecureChatServerInitializer (group, context);
    }

    public static void main(String[] args) throws CertificateException, SSLException {
        if (args.length != 1) {
            System.out.println ("Please give port as argument");
            System.exit (1);
        }
        int port = Integer.parseInt (args[0]);

        SelfSignedCertificate cert = new SelfSignedCertificate ( );
        SslContext context = SslContextBuilder
                .forServer (cert.certificate (),cert.privateKey ())
                .build ();

        final SecureChatServer endpoint = new SecureChatServer (context);
        ChannelFuture future = endpoint.start (new InetSocketAddress (port));
        Runtime.getRuntime ( ).addShutdownHook (new Thread (endpoint::destroy));
        future.channel ( ).closeFuture ( ).syncUninterruptibly ( );
    }
}
