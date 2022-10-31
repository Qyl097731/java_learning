package chapter12.demo04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * @date:2022/10/31 8:40
 * @author: qyl
 */
public class UDPTimeClient {
    public static final int PORT = 37;
    public static final String DEFAULT_HOST = "time.nist.gov";

    public static void main(String[] args) {
        InetAddress host;
        try {
            if (args.length > 0) {
                host = InetAddress.getByName(args[0]);
            } else {
                host = InetAddress.getByName(DEFAULT_HOST);
            }
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return;
        }
        UDPPoke poker = new UDPPoke(host,PORT);
        byte[] response = poker.poke();
        if (response == null){
            System.out.println("No response within allocated time");
            return;
        }else if (response.length != 4){
            System.out.println("Unrecognized response format");
            return;
        }

        // time协议时间起点是1900年 Java UDPPoke 是1979 ，需要转换
        long diff = 2208988800L;
        long secondsSince1900 = 0;
        for (int i = 0; i < 4; i++) {
            secondsSince1900 = (secondsSince1900 << 8) | (response[i] & 0x000000FF);
        }

        long secondsSince1970 = secondsSince1900 - diff;

        long msSince1970 = secondsSince1970 * 1000;
        Date time = new Date(msSince1970);
        System.out.println(time);
    }
}
