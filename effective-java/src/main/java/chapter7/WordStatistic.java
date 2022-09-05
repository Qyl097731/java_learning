package chapter7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @description
 * @date:2022/9/4 14:09
 * @author: qyl
 */
public class WordStatistic {
    public static void main(String[] args) {
        File file = new File(args[0]);
        long start = System.currentTimeMillis();
        fakeStream(file);
        long end = System.currentTimeMillis();
        System.out.println("time = " + (end - start));

        start = System.currentTimeMillis();
        Map<String, Long> map = stream(file);
        end = System.currentTimeMillis();
        System.out.println("time = " + (end - start));
        List<String> topToken = map.keySet().stream().sorted(Comparator.comparing(map::get).reversed())
                .limit(10)
                .collect(Collectors.toList());
        System.out.println(topToken);
    }

    private static void fakeStream(File file) {
        Map<String, Long> freq = new HashMap<>();
        try (Stream<String> words = new Scanner(file).tokens()) {
            words.forEach(word -> {
                freq.merge(word.toLowerCase(), 1L, Long::sum);
            });
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, Long> stream(File file) {
        Map<String, Long> freq = null;
        try (Stream<String> words = new Scanner(file).tokens()) {
            freq = words.collect(Collectors.groupingBy(String::toLowerCase, Collectors.counting()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return freq;
    }
}
