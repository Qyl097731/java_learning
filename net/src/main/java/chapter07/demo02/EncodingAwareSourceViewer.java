package chapter07.demo02;

import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description 用正确的字符接下载一个Web页面
 * @date:2022/10/26 9:57
 * @author: qyl
 */
public class EncodingAwareSourceViewer {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            String encoding = "ISO-8859-1";
            URL u = null;
            try {
                u = new URL(args[i]);
                URLConnection uc = u.openConnection();
                String contentType = uc.getContentType();
                int encodingStart = contentType.indexOf("charset=");
                if (encodingStart != -1) {
                    encoding = contentType.substring(encodingStart + 8);
                }
                try (Reader r = new InputStreamReader(new BufferedInputStream(uc.getInputStream()))) {
                    int c;
                    while ((c = r.read()) != -1) {
                        System.out.print((char) c);
                    }
                }
            } catch (MalformedURLException e) {
                System.err.println(args[0] + " is not a parsable URL");
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }
}
