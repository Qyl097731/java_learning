package chapter04.demo06;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 比较资源是否相等
 * @date:2022/10/25 13:44
 * @author: qyl
 */
public class URLEquality {
    public static void main(String[] args) {
        try{
            URL u = new URL(args[0]);
            URL ibiblio = new URL(args[1]);
            if (u.equals(ibiblio)){
                System.out.println(ibiblio + " is the same as " + u);
            }else {
                System.out.println(ibiblio + " is not the same as " + u);
            }
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
    }
}
