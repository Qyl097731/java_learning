package java8.chapter03.demo01;

import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * @description
 * @date:2022/10/24 21:26
 * @author: qyl
 */
public class FileLambdaTest {
    public static String processFile(BufferedReaderProcessor processor) throws IOException {
        try (BufferedReader br =
                     new BufferedReader(new FileReader("data.txt"))) {
            return processor.process(br);
        }
    }

    @Test
    public void test() throws IOException {
        /**
         * 处理一行
         */
        processFile(BufferedReader::readLine);
        /**
         * 处理两行
         */
        processFile(b -> b.readLine() + b.readLine());
    }
}
