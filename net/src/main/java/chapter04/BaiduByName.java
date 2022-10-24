package chapter04;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;

/**
 * @description 根据域名创建一个InetAddress
 * @date:2022/10/24 12:57
 * @author: qyl
 */
public class BaiduByName {
    public static void main(String[] args) throws UnknownHostException {
        InetAddress address = InetAddress.getByName("www.baidu.com");
        System.out.println(address);

        InetAddress host = InetAddress.getByName("180.101.49.13");
        // 如果请求的地址没有主机名就会直接返回IP地址
        System.out.println(host.getHostName());

        // 查询所有www.baidu.com对应的IP地址
        InetAddress[] addresses = InetAddress.getAllByName("www.baidu.com");
        Arrays.stream(addresses).forEach(System.out::println);

        // 连接DNS来得到真正的本地主机名和IP
        InetAddress localHost = InetAddress.getLocalHost();
        System.out.println(localHost);
    }
}
