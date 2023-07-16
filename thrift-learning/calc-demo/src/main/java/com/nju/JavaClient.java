package com.nju;

import com.nju.exception.InvalidOperation;
import com.nju.model.Operation;
import com.nju.model.SharedStruct;
import com.nju.model.Work;
import com.nju.service.Calculator;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSSLTransportFactory;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * @author qiuyiliang
 */
public class JavaClient {
    public static void main(String[] args) {
        if (args.length != 1){
            System.out.println("Please enter 'simple' or 'secure'");
            System.exit(0);
        }
        
        try{
            TTransport transport;
            if (args[0].contains("simple")){
                transport = new TSocket("localhost",9090);
                transport.open();
            }else {
                TSSLTransportFactory.TSSLTransportParameters params = new TSSLTransportFactory.TSSLTransportParameters();
                params.setTrustStore("/Users/qiuyiliang/projects/tech-learning/thrift-learning/calc-demo/src/main/java/com/nju/secret/.truststore", "thrift", "SunX509", "JKS");
                
                transport = TSSLTransportFactory.getClientSocket("localhost",9091,0, params);
            }
            TProtocol protocol = new TBinaryProtocol(transport);
            Calculator.Client client = new Calculator.Client(protocol);
            perform(client);
        } catch (TException e) {
            throw new RuntimeException(e);
        }
    }

    private static void perform(Calculator.Client client) throws TException
    {
        client.ping();
        System.out.println("ping()");

        int sum = client.add(1,1);
        System.out.println("1+1=" + sum);

        Work work = new Work();

        work.op = Operation.DIVIDE;
        work.num1 = 1;
        work.num2 = 0;
        try {
            int quotient = client.calculate(1, work);
            System.out.println("Whoa we can divide by 0");
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.why);
        }

        work.op = Operation.SUBTRACT;
        work.num1 = 15;
        work.num2 = 10;
        try {
            int diff = client.calculate(1, work);
            System.out.println("15-10=" + diff);
        } catch (InvalidOperation io) {
            System.out.println("Invalid operation: " + io.why);
        }

        SharedStruct log = client.getStruct(1);
        System.out.println("Check log: " + log.value);
    }
}