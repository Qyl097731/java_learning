package com.nju;

import com.nju.service.MultiplicationService;
import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;

/**
 * 乘法客户端
 * @author qiuyiliang
 */
public class MultiplicationClient {
    public static void main(String[] args) {
        try{
            TTransport transport;
            // 指定要连接的服务器和端口
            transport = new TSocket("localhost",9090);
            transport.open();
            // 用二进制协议对该会话中的数据进行序列化/反序列化
            TProtocol protocol = new TBinaryProtocol(transport);
            MultiplicationService.Client client = new MultiplicationService.Client(protocol);

            perform(client);

            transport.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void perform(MultiplicationService.Client client) throws TException {
        int product = client.multiply(123,2);
        System.out.printf("3 * 5 = product:%d\n", product);
    }


}
