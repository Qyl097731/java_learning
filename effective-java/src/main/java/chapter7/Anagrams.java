package chapter7;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * @description Stream的使用
 * @date:2022/9/4 11:29
 * @author: qyl
 */
public class Anagrams {
    public static void main(String[] args) {
        File dictionary = new File(args[0]);
        int minGroupSize = Integer.parseInt(args[1]);

        Map<String, Set<String>> groups = new HashMap<>();
        try (Scanner c = new Scanner(dictionary)) {
            while (c.hasNext()) {
                String word = c.next();
                groups.computeIfAbsent(alphabetize(word), (unused) -> new TreeSet<>()).add(word);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        for (Set<String> group : groups.values()) {
            if (group.size() > minGroupSize) {
                System.out.println(group.size() + " : " + group);
            }
        }
    }

    private static String alphabetize(String s) {
        char[] a = s.toCharArray();
        Arrays.sort(a);
        return new String(a);
    }
}
