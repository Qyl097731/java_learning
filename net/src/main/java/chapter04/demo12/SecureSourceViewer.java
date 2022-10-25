package chapter04.demo12;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.Authenticator;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 下载由口令认证的Web页面程序，通过DialogAuthenticator向用户询问账号密码
 * @date:2022/10/25 17:20
 * @author: qyl
 */
public class SecureSourceViewer {
    public static void main(String[] args) {
        // arg[i] 提供要认证的网站
        Authenticator.setDefault(new DialogAuthenticator());
        for (int i = 0; i < args.length; i++) {
            try {
                URL u = new URL(args[i]);
                try (Reader r = new InputStreamReader(new BufferedInputStream(u.openStream()))) {
                    int c;
                    while ((c = r.read()) != -1) {
                        System.out.print((char) c);
                    }
                } catch (IOException e) {
                    System.err.println(e);
                }
            } catch (MalformedURLException e) {
                System.err.println(args[0] + " is not a parsable URL");
            }
            System.out.println();
        }
        System.exit(0);
    }
}
