package chapter04.demo01;

import org.junit.jupiter.api.Test;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description 监视垃圾邮件发送者
 * 为了监视垃圾邮件来源IP，要处理相当大的网络负载要数千个甚至上万个主机反复查询试图建立连接的IP是不是一个已知的垃圾邮件发送者
 * @date:2022/10/24 16:22
 * @author: qyl
 */
public class SpamCheck {
    /**
     * 逆置地址的字节
     * 向BLACKHOLE发DNS请求，如果查找成功（返回127.0.0.2/127.0.0.1），就说明是一个垃圾邮件，否则是非垃圾邮件（UnknownHostException）
     */
    public static final String BLACKHOLE = "sbl.spamhaus.org";

    public static void main(String[] args) {
        for (String arg : args) {
            if (isSpammer(arg)) {
                System.err.println(arg + "is a known spammer.");
            } else {
                System.err.println(arg + " appears legitimate.");
            }
        }
    }

    private static boolean isSpammer(String arg) {
        try {
            InetAddress address = InetAddress.getByName(arg);
            byte[] quad = address.getAddress();
            String query = BLACKHOLE;
            for (byte octet : quad) {
                int unsignedByte = octet < 0 ? octet + 256 : octet;
                query = unsignedByte + "." + query;
            }
            InetAddress.getByName(query);
            return true;
        } catch (UnknownHostException e) {
            return false;
        }
    }
}
