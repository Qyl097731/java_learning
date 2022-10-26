package chapter07.demo03;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

/**
 * @description 从Web网站下载二进制文件并保存到磁盘 HTTP服务器发送数据后不会立即关闭连接，所以要知道何时停止读取
 * @date:2022/10/26 11:48
 * @author: qyl
 */
public class BinarySaver {
    public static void main(String[] args) {
        for (int i = 0; i < args.length; i++) {
            try {
                URL root = new URL(args[i]);
                saveBinaryFile(root);
            } catch (MalformedURLException e) {
                System.err.println(args[0] + " is not URL I understand");
            } catch (IOException ex) {
                System.err.println(ex);
            }
        }
    }

    private static void saveBinaryFile(URL u) throws IOException {
        URLConnection uc = u.openConnection();
        String contentType = uc.getContentType();
        int contentLength = uc.getContentLength();
        if (contentType.startsWith("text/") || contentLength != -1) {
            throw new IOException("This is not a binary file.");
        }
        try (InputStream in = new BufferedInputStream(uc.getInputStream())) {
            byte[] data = new byte[contentLength];
            int offset = 0;
            while (offset < contentLength) {
                int bytesRead = in.read(data, offset, data.length - offset);
                if (bytesRead == -1) break;
                offset += bytesRead;
            }
            if (offset != contentLength) {
                throw new IOException("Only read " + offset + " bytes; Expected " + contentLength + " bytes");
            }
            // 去掉文件路径 只留下文件名字
            String filename = u.getFile();
            filename = filename.substring(filename.lastIndexOf("/") + 1);
            try(FileOutputStream fout = new FileOutputStream(filename)) {
                fout.write(data);
                fout.flush();
            }
        }
    }
}
