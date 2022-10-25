package chapter04.demo04;

import java.io.*;
import java.net.URL;

/**
 * @description 下载一个Web页面
 * @date:2022/10/25 12:10
 * @author: qyl
 */
public class SourceViewer {
    public static void main(String[] args) {
        if (args.length > 0) {
            try {
                URL u = new URL(args[0]);
                try (Reader r = new InputStreamReader(new BufferedInputStream(u.openStream()))) {
                    int c;
                    while ((c = r.read()) != -1) {
                        System.out.print((char) c);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
