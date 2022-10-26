package chapter07.demo12;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 用URLConnection下载页面，同时请求失败之后，在catch块中调用getErrorStream()
 * @date:2022/10/26 19:57
 * @author: qyl
 */
public class SourceViewer4 {
    public static void main(String[] args) {
        try {
            URL u = new URL(args[0]);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            try (InputStream raw = uc.getInputStream()) {
                printFormStream(raw);
            } catch (IOException e) {
                printFormStream(uc.getErrorStream());
            }
        } catch (MalformedURLException e) {
            System.err.println(args[0] + " is not a parsable URL");
        } catch (IOException e) {
            System.err.println(e);
        }

    }

    private static void printFormStream(InputStream raw) throws IOException {
        try (Reader r = new InputStreamReader(new BufferedInputStream(raw))) {
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
        }
    }
}
