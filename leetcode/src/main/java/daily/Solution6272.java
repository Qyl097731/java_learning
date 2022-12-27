package daily;

/**
 * @description
 * @date:2022/12/27 23:44
 * @author: qyl
 */
public class Solution6272 {
    static final int mod = (int) 1e9 + 7;

    int add(int x, int y) {
        if ((x += y) >= mod) x -= mod;
        return x;
    }

    int sub(int x, int y) {
        if ((x -= y) < 0) x += mod;
        return x;
    }

    public int countPartitions(int[] nums, int k) {
        int res = 1;
        int n = nums.length;

        long sum = 0;
        for (int num : nums) {
            sum += num;
        }
        if (sum < 2 * k) {
            return 0;
        }

        for (int i = 0; i < n; i++) {
            res = (res * 2) % mod;
        }
        int[] dp = new int[k];
        dp[0] = 1;
        for (int x : nums) {
            for (int j = k - 1; j >= x; j--) {
                dp[j] = add (dp[j], dp[j - x]);

            }
        }
        for (int num : dp) {
            res = sub (res, num);
            res = sub (res, num);
        }
        return res;
    }
}
