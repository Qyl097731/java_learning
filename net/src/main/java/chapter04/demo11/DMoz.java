package chapter04.demo11;

import chapter04.demo08.QueryString;
import org.junit.jupiter.api.Assertions;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 通过URL进行get请求
 * @date:2022/10/25 16:36
 * @author: qyl
 */
public class DMoz {
    public static void main(String[] args) {
        QueryString query = new QueryString();
        if (args.length != 0) {
            query.add("q", args[0]);
        }
        try {
            URL u = new URL("http://www.dmoz.org/search?" + query);
            try (Reader r = new InputStreamReader(new BufferedInputStream(u.openStream()))) {
                int c;
                while ((c = r.read()) != -1) {
                    System.out.println((char) c);
                }
            } catch (IOException e) {
                System.err.println(e);
            }
        } catch (MalformedURLException e) {
            System.err.println(e);
        }
    }
}
