package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/8 10:40
 * @author: qyl
 */
public class Solution6284 {
    public boolean isItPossible(String word1, String word2) {
        int[] cnt1 = new int[26], cnt2 = new int[26];
        int n = word1.length ();
        for (int i = 0; i < n; i++) {
            char c = word1.charAt (i);
            cnt1[c - 'a']++;
        }
        for (int i = 0; i < word2.length (); i++) {
            char c = word2.charAt (i);
            cnt2[c - 'a']++;
        }
        boolean flag = false;
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                if (cnt1[i] != 0 && cnt2[j] != 0) {
                    cnt1[i]--;
                    cnt2[j]--;
                    cnt1[j]++;
                    cnt2[i]++;
                    flag |= check (cnt1, cnt2);
                    cnt1[i]++;
                    cnt2[j]++;
                    cnt1[j]--;
                    cnt2[i]--;
                }
            }
        }
        return flag;
    }

    private boolean check(int[] cnt1, int[] cnt2) {
        return Arrays.stream (cnt1).filter (c -> c != 0).count () == Arrays.stream (cnt2).filter (c -> c != 0).count ();
    }

    @Test
    public void test() {
        System.out.println (isItPossible ("ac",
                "b"));
    }
}
