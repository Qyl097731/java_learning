package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/5 19:06
 * @author: qyl
 */
public class Solution2347 {
    public String bestHand(int[] ranks, char[] suits) {
        int[] s = new int[4];
        for (char suit : suits) {
            s[suit - 'a']++;
        }
        for (int i = 0; i < 4; i++) {
            if (s[i] == 5) {
                return "Flush";
            }
        }
        int[] cnts = new int[14];
        for (int rank : ranks) {
            cnts[rank]++;
        }
        Arrays.sort (cnts);
        if (cnts[cnts.length - 1] >= 3) {
            return "Three of a Kind";
        } else if (cnts[cnts.length - 1] >= 2) {
            return "Pair";
        } else {
            return "High Card";
        }
    }
}
