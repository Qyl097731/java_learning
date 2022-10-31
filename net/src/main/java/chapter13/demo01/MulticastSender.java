package chapter13.demo01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * @description 发送组播数据
 * @date:2022/10/31 13:45
 * @author: qyl
 */
public class MulticastSender {
    public static void main(String[] args) {
        InetAddress ia = null;
        int port = 0;
        byte ttl = (byte) 1;
        // 命令行读取地址
        try {
            ia = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
            if (args.length > 2) ttl = (byte) Integer.parseInt(args[2]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        byte[] data = "Here's some multicast data\r\n".getBytes();
        DatagramPacket dp = new DatagramPacket(data, data.length, ia, port);
        try(MulticastSocket ms = new MulticastSocket()) {
            ms.setTimeToLive(ttl);
            ms.joinGroup(ia);
            for (int i = 1; i < 10; i++) {
                ms.send(dp);
            }
            ms.leaveGroup(ia);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
