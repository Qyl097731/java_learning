package day28;

/**
 * @description
 * @date:2022/10/20 15:08
 * @author: qyl
 */
class Solution1953 {
    public long numberOfWeeks(int[] milestones) {
        long res = 0;
        int n = milestones.length;
        if (n == 1) {
            return 1;
        }
        int max = 0;
        long tot = 0;
        for (int milestone : milestones) {
            tot += milestone;
            max = Math.max(max, milestone);
        }
        if (max - 1 > tot - max) {
            return 1 + 2 * (tot - max);
        }
        return tot;
    }
}
