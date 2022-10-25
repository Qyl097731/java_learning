package chapter04.demo07;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @description 分解URI
 * @date:2022/10/25 14:38
 * @author: qyl
 */
public class URISplitter {
    public static void main(String[] args) {
        for (String arg : args) {
            try {
                URI u = new URI(arg);
                System.out.println("uri is " + u);
                // 判断是不是透明的URI
                if (u.isOpaque()) {
                    System.out.println("This is an opaque URI.");
                    System.out.println("The scheme is " + u.getScheme());
                    System.out.println("The scheme specific part is " + u.getSchemeSpecificPart());
                    System.out.println("The fragment ID is " + u.getFragment());
                } else {
                    System.out.println("This is a hierarchical URI.");
                    System.out.println("The scheme is " + u.getScheme());
                    try {
                        // 一般Java不会事先检查授权机构的语法错误，这里通过parseServerAuthority检查
                        // 如果没有问题才解析
                        u = u.parseServerAuthority();
                        System.out.println("The host is " + u.getHost());
                        System.out.println("The user info is " + u.getUserInfo());
                        System.out.println("The port is " + u.getPort());
                    } catch (URISyntaxException ex) {
                        System.out.println("The authority is " + u.getAuthority());
                    }
                    System.out.println("The path is " + u.getPath());
                    System.out.println("The query string is " + u.getQuery());
                    System.out.println("The fragment ID is " + u.getFragment());
                }
            } catch (URISyntaxException e) {
                System.err.println(arg + " does not seem to be a URL.");
            }
            System.out.println();
        }
    }
}
