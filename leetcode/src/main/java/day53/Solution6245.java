package day53;

/**
 * @description
 * @date:2022/11/27 10:29
 * @author: qyl
 */
public class Solution6245 {
    public int pivotInteger(int n) {
        int res = -1;
        for (int i = 0; i <= n; i++) {
            int sum1 = 0, sum2 = 0;
            for (int j = 0; j <= i; j++) {
                sum1 += j;
            }

            for (int j = i; j <= n; j++) {
                sum2 += j;
            }

            if (sum1 == sum2) {
                return i;
            }
        }
        return 0;
    }
}
