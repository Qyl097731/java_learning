package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/17 18:30
 * @author: qyl
 */
public class Solution1813 {
    public boolean areSentencesSimilar(String sentence1, String sentence2) {
        String[] s1 = sentence1.split (" "), s2 = sentence2.split (" ");
        int i = 0, j = 0;
        while (i < s1.length && i < s2.length && s1[i].equals (s2[i])) i++;
        while (s1.length - 1 - j >= i && s2.length - j - 1 >= i &&
                s1[s1.length - 1 - j].equals (s2[s2.length - j - 1])) j++;
        return i + j == Math.min (s1.length, s2.length);
    }

    @Test
    public void test() {
        areSentencesSimilar ("of", "a lot of w");
    }
}
