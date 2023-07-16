package com.nju.server;

import com.nju.service.Calculator;
import com.nju.service.impl.CalculatorHandler;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.*;

/**
 * @author qiuyiliang
 */
public class JavaServer {
    public static CalculatorHandler handler;
    public static Calculator.Processor processor;
    
    public static void main(String[] args) {
        try{
            handler = new CalculatorHandler();
            processor = new Calculator.Processor(handler);
            
            Runnable simple = new Runnable() {
                @Override
                public void run() {
                    simple(processor);
                }
            };
            
            Runnable secure = new Runnable() {
                @Override
                public void run() {
                    secure(processor);
                }
            };
            
            new Thread(simple).start();
            new Thread(secure).start();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    private static void simple(Calculator.Processor processor){
        try{
            TServerTransport serverTransport = new TServerSocket(9090);
            
            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            System.out.println("start the simple server");
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
    
    private static void secure(Calculator.Processor processor){
        try{
            TSSLTransportFactory.TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters();
            // 私钥
            params.setKeyStore("/Users/qiuyiliang/projects/tech-learning/thrift-learning/calc-demo/src/main/java/com/nju/secret/.keystore", "thrift", null, null);
            TServerTransport serverTransport = TSSLTransportFactory.getServerSocket(9091, 0, null, params);

            TServer server = new TSimpleServer(new TServer.Args(serverTransport).processor(processor));
            System.out.println("Starting the secure server...");
            server.serve();
        } catch (TTransportException e) {
            throw new RuntimeException(e);
        }
    }
}
