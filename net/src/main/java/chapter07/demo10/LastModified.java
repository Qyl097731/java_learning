package chapter07.demo10;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;

/**
 * @description 通过HEAD获取资源最后一次修改的时间
 * @date:2022/10/26 19:17
 * @author: qyl
 */
public class LastModified {
    public static void main(String[] args) throws IOException {
        for (int i = 0; i < args.length; i++) {
            URL u = new URL(args[i]);
            HttpURLConnection http = (HttpURLConnection) u.openConnection();
            http.setRequestMethod("HEAD");
            System.out.println(u + " was last modified at " + new Date(http.getLastModified()));
        }
    }
}
