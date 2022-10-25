package chapter04.demo03;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 测试Java支持哪些URL协议，自己传入testProtocol指定的url地址；如下展示几条
 * @date:2022/10/25 11:12
 * @author: qyl
 */
public class ProtocolTester {
    public static void main(String[] args) {
        // 超文本传输协议
        testProtocol("http://www.adc.org");
        //  安全http
        testProtocol("https://www.amazon.com/exec/obidos/order2/");
        // 文件传输协议
        testProtocol("ftp://ibiblio.org/pub/languages/java/javafaq/");
        // 简单邮件传输协议
        testProtocol("mailto:elharo@ibiblio.org");
        // telnet
        testProtocol("telnet://dibner.poly.edu/");
        // 本地文件访问
        testProtocol("file:///etc/passwd");

        // gopher
        // ldap 轻量组目录访问协议
        // jar
        // NFS 网络文件系统
        // JDBC
        // rmi 远程方法调用的定制协议
        // HotJava 地址协议: doc、netdoc、systemresource、verbatim
    }

    private static void testProtocol(String url) {
        URL u = null;
        try {
            u = new URL(url);
            System.out.println(u.getProtocol() + " is supported");
        } catch (MalformedURLException e) {
            String protocol = url.substring(0, url.indexOf(':'));
            System.out.println(protocol + " is not supported");
        }
    }
}
