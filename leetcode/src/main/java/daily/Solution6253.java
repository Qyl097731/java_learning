package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/5 11:53
 * @author: qyl
 */
public class Solution6253 {
    public boolean isCircularSentence(String sentence) {
        for (int i = 0; i != -1; i++) {
            i = sentence.indexOf (' ', i);
            if (i == -1) {
                break;
            }
            if (sentence.charAt (i - 1) != sentence.charAt (i + 1)) {
                return false;
            }
        }
        return sentence.charAt (0) == sentence.charAt (sentence.length ( ) - 1);
    }

    @Test
    public void test() {
        isCircularSentence ("leetcode e");
    }
}
