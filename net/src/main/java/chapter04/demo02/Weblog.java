package chapter04.demo02;

import java.io.*;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @description 读取Web服务器的日志文件，显示各行时将IP地址转换为主机名
 * @date:2022/10/24 16:40
 * @author: qyl
 */
public class Weblog {
    /**
     * 大量解析的时候，由于IO和DNS时间的差别，导致CPU资源浪费
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        try (BufferedReader bin = new BufferedReader(new InputStreamReader(new FileInputStream(args[0])))) {
            for (String entry = bin.readLine(); entry != null; entry = bin.readLine()) {
                // 分解IP
                int index = entry.indexOf(' ');
                String ip = entry.substring(0, index);
                String theRest = entry.substring(index);
                // 向DNS请求主机名并显示
                try {
                    InetAddress address = InetAddress.getByName(ip);
                    System.out.println(address.getHostName() + theRest);
                } catch (UnknownHostException ex) {
                    System.err.println(entry);
                }
            }
        }
    }
}
