package templates.dp;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/10/10 9:52
 * @author: qyl
 */
public class NumericDp {
    long[][] dp = new long[20][6];
    int[] digit = new int[20];

    void init() {
        for (int i = 0; i < dp.length; i++) {
            Arrays.fill(dp[i], -1);
        }
    }

    long dfs(int pos, int pre, int sta, boolean limit) {  //以53421为例
        if (pos == -1) {
            return 1;                   //代表这种情况搜索结束，+1
        }
        // dp[pos][sta] 表示 dp[pos][num]的和（num开头的pos位数） 降低了空间复杂度
        if (!limit && dp[pos][sta] != -1) {
            return dp[pos][sta]; // 如果不是要求的数字， 那么有记录了就直接返回就行了。
        }
        int up = limit ? digit[pos] : 9; // 如果没有限制 那么当前位置的最大可以枚举到9
        long sum = 0;
        for (int i = 0; i <= up; i++) {                                       //每一位进行枚举
            if (pre == 4 && i == 9) {
                continue;                                 //符合条件的不搜了
            }
            sum += dfs(pos - 1, i, i == 4 ? 0 : 1, limit && i == digit[pos]);             //不符合条件的加上，pre记录这一位的值，sta记录是否有可能成为49
            // ，最后一个表示是否有限制
        }
        if (!limit) {           // 如果不是边界就记录，如果把边界也记录了sum+会出现重复
            dp[pos][sta] = sum;                        //没有限制将dp的数值存起来，以便调用
        }
        return sum;                                        //返回值
    }

    long solve(long a) {
        int cnt = 0;                       //分解这个数
        while (a > 0) {
            digit[cnt++] = (int) a % 10;
            a /= 10;
        }
        long ans = dfs(cnt - 1, 0, 0, true);          //对这个数进行dfs
        return ans;
    }

    @Test
    public void test1() {
        init();
        long res = solve(54321L);
        Assertions.assertEquals(res, 1);
    }
}
