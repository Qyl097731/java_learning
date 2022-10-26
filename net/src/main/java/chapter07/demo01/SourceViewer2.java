package chapter07.demo01;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Observable;

/**
 * @description 用URLConnection下载一个Web页面
 * @date:2022/10/26 9:43
 * @author: qyl
 */
public class SourceViewer2 extends Observable {
    public static void main(String[] args) {
        if (args.length > 0){
            try {
                URL u = new URL(args[0]);
                URLConnection uc = u.openConnection();
                try(Reader r = new InputStreamReader(new BufferedInputStream(uc.getInputStream()))){
                    int c;
                    while ((c = r.read()) != -1){
                        System.out.print((char)c);
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
