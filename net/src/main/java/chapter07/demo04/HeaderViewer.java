package chapter07.demo04;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Date;

/**
 * @description 返回首部
 * @date:2022/10/26 12:55
 * @author: qyl
 */
public class HeaderViewer {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                URL u = new URL(args[0]);
                URLConnection uc = u.openConnection();
                System.out.println("Content-type: " + uc.getContentType());
                if (uc.getContentEncoding() != null) {
                    System.out.println("Content-encoding: " + uc.getContentEncoding());
                }
                if (uc.getDate() != 0) {
                    System.out.println("Date: " + new Date(uc.getDate()));
                }
                if (uc.getLastModified() != 0) {
                    System.out.println("Last Modified: " + new Date(uc.getLastModified()));
                }
                if (uc.getExpiration() != 0) {
                    System.out.println("Expiration date: " + new Date(uc.getExpiration()));
                }
                if (uc.getContentLength() != -1){
                    System.out.println("Content-length: " + uc.getContentLength());
                }
            } catch (MalformedURLException e) {
                System.err.println(args[0] + " is not a URL I understand");
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
