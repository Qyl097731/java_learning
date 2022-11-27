package day53;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/26 22:19
 * @author: qyl
 */
public class Solution6251 {
    public int bestClosingTime(String customers) {
        int n = customers.length ( );
        int[] pre = new int[n + 1];
        int temp = 0;
        int res = 0, mav = Integer.MAX_VALUE;
        char[] chars = customers.toCharArray ( );

        for (int i = 1; i <= n; i++) {
            temp += chars[i - 1] == 'Y' ? 1 : 0;
            pre[i] = temp;
        }

        for (int i = 1; i <= n + 1; i++) {
            int t1 = i - 1 - pre[i - 1];
            int t2 = pre[n] - pre[i - 1];
            int cal = t1 + t2;
            if (cal < mav) {
                mav = cal;
                res = i - 1;
            }
        }
        return res;

    }

    @Test
    public void test() {
        String s = "YYNY";
        System.out.println (bestClosingTime (s));
        String s2 = "NNNNN";
        System.out.println (bestClosingTime (s2));

        String s3 = "YYYY";
        System.out.println (bestClosingTime (s3));
    }
}
