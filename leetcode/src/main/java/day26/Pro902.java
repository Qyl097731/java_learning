package day26;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @author: qyl
 */
class Solution902 {
    int[] dp = new int[20];
    int[] digit = new int[20];
    boolean[] exist = new boolean[10];

    public int atMostNGivenDigitSet(String[] digits, int n) {
        for (int i = 0; i < digits.length; i++) {
            exist[Integer.parseInt(digits[i])] = true;
        }
        init();
        return solve(n);
    }

    void init() {
        Arrays.fill(dp, -1);
    }

    int solve(int a) {
        int cnt = 0;                       //分解这个数
        while (a > 0) {
            digit[cnt++] = a % 10;
            a /= 10;
        }
        return dfs(cnt - 1, 0, true);
    }

    int dfs(int pos, int sta, boolean limit) {
        if (pos == -1) {
            return sta;                   //代表这种情况搜索结束
        }
        // dp[pos][sta] 表示pos位的时候  前面填没填数字
        if (!limit && dp[pos] != -1 && sta == 1) {
            return dp[pos]; // 如果之前填写过数字了
        }
        int up = limit ? digit[pos] : 9; // 如果没有限制 那么当前位置的最大可以枚举到9
        int sum = 0;
        if (sta == 0) {
            sum += dfs(pos - 1, 0, false);
        }
        for (int i = 0; i <= up; i++) {
            //每一位进行枚举
            if (exist[i]) {
                sum += dfs(pos - 1, 1, limit && i == digit[pos]);             //不符合条件的加上，pre记录这一位的值，sta
            }
        }
        if (!limit && sta == 1) {           // 如果不是边界就记录，如果把边界也记录了sum+会出现重复
            dp[pos] = sum;                        //没有限制将dp的数值存起来，以便调用
        }
        return sum;                                        //返回值
    }
}
