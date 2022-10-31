package chapter13.demo01;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.MulticastSocket;
import java.net.UnknownHostException;

/**
 * @description 接受特定主机的组播数据，且只能时二进制数据
 * @date:2022/10/31 13:39
 * @author: qyl
 */
public class MulticastSniffer {
    public static void main(String[] args) {
        InetAddress group = null;
        int port = 0;

        // 从命令行读取地址
        try {
            group = InetAddress.getByName(args[0]);
            port = Integer.parseInt(args[1]);
        } catch (UnknownHostException e) {
            e.printStackTrace();
            System.exit(1);
        }

        MulticastSocket ms = null;
        try{
            ms = new MulticastSocket(port);
            ms.joinGroup(group);
            byte[] buffer = new byte[8192];
            while (true){
                DatagramPacket dp = new DatagramPacket(buffer, buffer.length);
                ms.receive(dp);
                String s = new String(dp.getData(),"8859_1");
                System.out.println(s);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (ms!=null){
                try {
                    ms.leaveGroup(group);
                    ms.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
