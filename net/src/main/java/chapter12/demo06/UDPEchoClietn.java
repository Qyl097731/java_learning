package chapter12.demo06;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;

/**
 * @description UDP客户端一个接受消息线程，一个发送消息进程，异步处理，因为UDP不能保证数据能够发到
 * @date:2022/10/31 9:06
 * @author: qyl
 */
public class UDPEchoClietn {
    public final static int PORT = 7;

    public static void main(String[] args) {
        String hostname ="localhost";

        if (args.length > 0 ){
            hostname = args[0];
        }

        try {
            InetAddress ia = InetAddress.getByName(hostname);
            DatagramSocket socket = new DatagramSocket();
            SenderThread sender = new SenderThread(ia,socket,PORT);
            sender.start();
            ReceiverThread receiver = new ReceiverThread(socket);
            receiver.start();
        } catch (UnknownHostException | SocketException e) {
            e.printStackTrace();
        }
    }
}
