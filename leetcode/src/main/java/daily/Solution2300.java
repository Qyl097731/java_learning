package daily;

/**
 * @description
 * @date:2022/12/10 16:49
 * @author: qyl
 */
public class Solution2300 {
    public int[] successfulPairs(int[] spells, int[] potions, long success) {
        int n = spells.length, m = potions.length;
        int[] cnt = new int[100005];
        int[] res = new int[n];

        for (int i = 0; i < m; i++) {
            cnt[potions[i]]++;
        }

        for (int i = 1; i < cnt.length; i++) {
            cnt[i] += cnt[i - 1];
        }

        for (int i = 0; i < n; i++) {
            long div = (success + spells[i] - 1) / spells[i];
            if (div < 100005) {
                res[i] = cnt[100004] - cnt[(int) div - 1];
            }
        }
        return res;
    }
}
