package com.nju.server;

import com.nju.service.MultiplicationService;
import com.nju.service.impl.MultiplicationHandler;
import org.apache.thrift.server.THsHaServer;
import org.apache.thrift.server.TNonblockingServer;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 乘法服务器
 * @author qiuyiliang
 */
public class MultiplicationServer {
    public static MultiplicationHandler handler;
    
    public static MultiplicationService.Processor processor;
    
    public static void main(String[] args) {
        try{
            handler = new MultiplicationHandler();
            processor = new MultiplicationService.Processor(handler);

            Runnable simple = new Runnable() {
                public void run() {
                    simple(processor);
                }
            };
            new Thread(simple).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public static void simple(MultiplicationService.Processor processor){
        try{
            // 指定哪种方式进行数据传输，这里采用Socket，并且监听9090端口
            TServerTransport serverTransport = new TServerSocket(9090);
            // 使用标准阻塞I/O的单线程服务器。适用于测试。
            // 建造者模式来进行服务器初始化的参数构造
            TSimpleServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the simple server...");
            server.serve();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
