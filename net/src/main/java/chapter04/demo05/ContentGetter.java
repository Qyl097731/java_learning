package chapter04.demo05;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 下载一个对象
 * @date:2022/10/25 13:17
 * @author: qyl
 */
public class ContentGetter {
    public static void main(String[] args) {
        if (args.length > 0){
            try {
                URL u = new URL(args[0]);
                Object o = u.getContent();
                System.out.println(o.getClass().getName());
            } catch (MalformedURLException ex) {
                System.err.println(args[0] + " is not a parsable URL");
            } catch (IOException e) {
                System.err.println(e);
            }
        }
    }
}
