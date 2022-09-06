package chapter9;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * @description
 * @date:2022/9/6 20:24
 * @author: qyl
 */
public class URLPrint {
    public static void main(String[] args) {
        try(InputStream in = new URL(args[0]).openStream()){
            in.transferTo(System.out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
