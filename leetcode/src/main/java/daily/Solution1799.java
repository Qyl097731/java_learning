package daily;

/**
 * @description
 * @date:2022/12/24 14:10
 * @author: qyl
 */
public class Solution1799 {
    public int maxScore(int[] nums) {
        int n = nums.length;
        int[][] gcdArray = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                gcdArray[i][j] = gcd (nums[i], nums[j]);
            }
        }
        int[] dp = new int[n];
        int all = 1 << n;
        for (int l = 1; l < all; l++) {
            int cnt = Integer.bitCount (l);
            if ((cnt & 1) == 1) {
                continue;
            }
            for (int i = 0; i < n; i++) {
                if (((l >> i) & 1) == 1) {
                    for (int j = i + 1; j < n; j++) {
                        if (((l >> j) & 1) == 1) {
                            dp[l] = Math.max (dp[l], dp[l ^ (1 << i) ^ (1 << j)] + cnt / 2 * gcdArray[i][j]);
                        }
                    }
                }
            }
        }
        return dp[all - 1];
    }

    private int gcd(int a, int b) {
        return b == 0 ? a : gcd (b, a % b);
    }
}
