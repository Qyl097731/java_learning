package chapter03.demo6;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.logging.Logger;
import java.util.zip.GZIPOutputStream;

/**
 * @description
 * @date:2022/10/24 9:13
 * @author: qyl
 */
public class GZipRunnable implements Runnable {
    private final File input;

    public GZipRunnable(File input) {
        this.input = input;
    }

    @Override
    public void run() {
        if (!input.getName().endsWith(".gz")) {
            File output = new File(input.getParent(), input.getName() + ".gz");
            // 不覆盖已经存在的文件
            if (!output.exists()) {
                try (InputStream in = new BufferedInputStream(new FileInputStream(input));
                     OutputStream out = new BufferedOutputStream(new GZIPOutputStream(new FileOutputStream(output)))) {
                    int b;
                    while ((b = in.read()) != -1) out.write(b);
                    out.flush();
                } catch (IOException e) {
                    Logger.getGlobal().info(e.getMessage());
                }
            }
        }
    }
}
