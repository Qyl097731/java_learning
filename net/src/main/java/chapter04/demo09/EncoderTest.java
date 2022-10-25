package chapter04.demo09;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @description x-www-for-urlencoded 字符串 这里大家可以自己添加更多尝试的字符串
 * @date:2022/10/25 15:09
 * @author: qyl
 */
public class EncoderTest {
    public static void main(String[] args) {
        try {
            System.out.println(URLEncoder.encode("This string has spaces","UTF-8"));
            System.out.println(URLEncoder.encode("This*string*has*asterisks","UTF-8"));
            // 这部分过度编码
            System.out.println(URLEncoder.encode("https://www.google.com/search?h1=en&as_q=Java&as_epq=I/O","UTF-8"));
        } catch (UnsupportedEncodingException e) {
            throw new RuntimeException("Broken VM does not support UTF-8");
        }

    }
}
