package java8.chapter03.demo01;

import java.io.BufferedReader;
import java.io.IOException;

/**
 * @description
 * @date:2022/10/24 21:29
 * @author: qyl
 */
@FunctionalInterface
public interface BufferedReaderProcessor {
    String process(BufferedReader b) throws IOException;
}
