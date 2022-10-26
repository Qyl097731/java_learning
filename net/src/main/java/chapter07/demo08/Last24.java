package chapter07.demo08;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @description 将ifModifiedSince设置为24小时之前
 * @date:2022/10/26 15:44
 * @author: qyl
 */
public class Last24 {
    public static void main(String[] args) throws IOException {
        Date today = new Date();
        long millisecondsPerDay = 24 * 60 * 60 * 1000;
        for (int i = 0; i < args.length; i++) {
            URL u = new URL(args[i]);
            URLConnection uc = u.openConnection();
            System.out.println("Original if modified since: " + new Date(uc.getIfModifiedSince()));
            uc.setIfModifiedSince((new Date(today.getTime() - millisecondsPerDay)).getTime());
            System.out.println("Will retrieve file if it's modified since " + new Date(uc.getIfModifiedSince()));
            try (Reader r = new InputStreamReader(new BufferedInputStream(uc.getInputStream()))) {
                int c;
                while ((c = r.read()) != -1) {
                    System.out.print((char) c);
                }
            }
            System.out.println();
        }
    }
}
