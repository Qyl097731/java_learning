package chapter07.demo11;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * @description 查看响应码和消息
 * @date:2022/10/26 19:52
 * @author: qyl
 */
public class SourceViewer3 {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            URL u = new URL(args[i]);
            HttpURLConnection uc = (HttpURLConnection) u.openConnection();
            int code = uc.getResponseCode();
            String response = uc.getResponseMessage();
            // 返回响应码和消息
            System.out.println("HTTP/1.x " + code + " " + response);
            for (int j = 0; ; j++) {
                String header = uc.getHeaderField(j);
                String key = uc.getHeaderFieldKey(j);
                if (header == null || key == null) break;
                System.out.println(key + " : " + header);
            }
        }
    }
}
