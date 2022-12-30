package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/30 18:59
 * @author: qyl
 */
public class Offer46 {
    public int translateNum(int num) {
        int cnt = 0, numCpy = num;
        int[] nums = new int[20];
        while (numCpy > 0) {
            nums[cnt++] = numCpy % 10;
            numCpy /= 10;
        }
        if (cnt == 0) {
            return 1;
        }
        int[] numsCpy = new int[cnt];
        for (int i = 0; i < cnt; i++) {
            numsCpy[cnt - i - 1] = nums[i];
        }
        int[] dp = new int[cnt + 1];
        dp[1] = dp[0] = 1;
        for (int i = 2; i <= cnt; i++) {
            dp[i] = dp[i - 1];
            if (numsCpy[i - 2] != 0 && (numsCpy[i - 2] * 10 + numsCpy[i - 1] <= 25)) {
                dp[i] += dp[i - 2];
            }
        }
        return dp[cnt];
    }

    @Test
    public void test() {
        translateNum (25);
    }
}
