package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/2 17:09
 * @author: qyl
 */
public class Solution1769 {
    public int[] minOperations(String boxes) {
//        return solveA(boxes);
        return solveB (boxes);
    }

    private int[] solveB(String boxes) {
        int n = boxes.length ( );
        int[] res = new int[n];
        int operations = 0, left = boxes.charAt (0) - '0', right = 0;
        for (int i = 1; i < n; i++) {
            if (boxes.charAt (i) == '1') {
                right++;
                operations += i;
            }
        }

        res[0] = operations;
        for (int i = 1; i < n; i++) {
            operations += left - right;
            if (boxes.charAt (i) == '1') {
                left++;
                right--;
            }
            res[i] = operations;
        }
        return res;
    }

    private int[] solveA(String boxes) {
        int n = boxes.length ( );
        int[] res = new int[n];
        int[] pre = new int[n + 1], post = new int[n + 1];
        int[] psum = new int[n + 1], pstSum = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            pre[i] = pre[i - 1] + (boxes.charAt (i - 1) == '1' ? 1 : 0);
        }
        for (int i = 1; i <= n; i++) {
            psum[i] = psum[i - 1] + pre[i - 1];
        }
        for (int i = n - 1; i >= 0; i--) {
            post[i] = post[i + 1] + (boxes.charAt (i) == '1' ? 1 : 0);
        }
        for (int i = n - 1; i >= 0; i--) {
            pstSum[i] = post[i + 1] + pstSum[i + 1];
        }
        for (int i = 0; i < n; i++) {
            res[i] = pstSum[i] + psum[i + 1];
        }
        return res;
    }


    @Test
    public void test() {
        minOperations ("001011");
        minOperations ("110");
    }
}
