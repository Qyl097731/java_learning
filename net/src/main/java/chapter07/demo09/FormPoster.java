package chapter07.demo09;

import chapter04.demo08.QueryString;

import javax.net.ssl.HttpsURLConnection;
import java.io.*;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description 把命令行地址或者默认地址作为请求地址，之后添加两个参数，最后通过法案送数据完成请求
 * @date:2022/10/26 18:34
 * @author: qyl
 */
public class FormPoster {
    private URL url;
    private QueryString query = new QueryString();

    public FormPoster(URL url) {
        if (!url.getProtocol().toLowerCase().startsWith("http")) {
            throw new IllegalArgumentException("Posting only works for http URLs");
        }
        this.url = url;
    }

    public void add(String name, String value) {
        query.add(name, value);
    }

    public URL getUrl() {
        return url;
    }

    public InputStream post() throws IOException {
        // 打开连接，准备POSt
        URLConnection uc = url.openConnection();
        uc.setDoOutput(true);
        try (OutputStreamWriter out = new OutputStreamWriter(new BufferedOutputStream(uc.getOutputStream()), "UTF-8")) {
            // POST行、Content-type首部和Content-length首部
            // 由URLConnection发送
            // 我们只需要发送数据
            out.write(query.toString());
            out.write("\r\n");
            out.flush();
        }

        // 返回响应
        return uc.getInputStream();
    }

    public static void main(String[] args) {
        URL url;
        if (args.length > 0) {
            try {
                url = new URL(args[0]);
            } catch (MalformedURLException e) {
                System.err.println("Usage: java FormPoster url");
                return;
            }
        } else {
            try {
                url = new URL("http://www.cafeaulait.org/books/jnp4/postquery.phtml");
            } catch (MalformedURLException e) {
                System.err.println(e);
                return;
            }
        }

        FormPoster poster = new FormPoster(url);
        poster.add("name", "test");
        poster.add("email", "test");
        try (Reader r = new InputStreamReader(poster.post())) {
            int c;
            while ((c = r.read()) != -1) {
                System.out.print((char) c);
            }
            System.out.println();
        } catch (IOException e) {
            System.err.println(e);
        }
    }
}
