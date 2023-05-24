package util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class StreamReaderThread implements Runnable {
    private Logger logger = LoggerFactory.getLogger(StreamReaderThread.class);

    /**
     * 输出流
     **/
    private InputStream inputStream;
    /**
     * 输出信息保存的文件名称
     **/
    private String logName;

    public StreamReaderThread(InputStream inputStream, String logName) {
        this.inputStream = inputStream;
        this.logName = logName;
    }

    /**
     * FileWriter将日志写入某文件
     * 也可以用logger打印日志记录。
     */
    public void run() {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(this.inputStream, "gbk"));
             FileWriter fileWriter = new FileWriter(logName, true)) {

            String line = null;
            while ((line = in.readLine()) != null) {
                fileWriter.write(line);
                logger.info(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}