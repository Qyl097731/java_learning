package com.nju.netty.ch13.demo01;

import java.net.InetSocketAddress;

/**
 * @description POJO类
 * @date:2022/11/26 21:55
 * @author: qyl
 */
public class LogEvent {
    public static final byte SEPARATOR = (byte) ':';
    private final InetSocketAddress source;
    private final String logfile;
    private final String msg;
    private final long received;

    // 传出消息的构造函数
    public LogEvent(String logfile, String msg) {
        this (null, -1, logfile, msg);
    }

    // 传入消息的构造函数
    public LogEvent(InetSocketAddress source, long received, String logfile, String msg) {
        this.source = source;
        this.logfile = logfile;
        this.msg = msg;
        this.received = received;
    }

    public InetSocketAddress getSource() {
        return source;
    }

    public String getLogfile() {
        return logfile;
    }

    public String getMsg() {
        return msg;
    }

    // 接收到LogEvent的时间
    public long getReceivedTimestamp() {
        return received;
    }
}
