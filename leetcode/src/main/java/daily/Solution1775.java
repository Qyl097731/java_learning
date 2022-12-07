package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/7 20:52
 * @author: qyl
 */
public class Solution1775 {
    int sum1, sum2;

    public int minOperations(int[] nums1, int[] nums2) {
        sum1 = Arrays.stream (nums1).sum ( );
        sum2 = Arrays.stream (nums2).sum ( );

        if (sum2 < sum1) {
            return solve (nums2, nums1, sum1 - sum2);
        } else {
            return solve (nums1, nums2, sum2 - sum1);
        }
    }

    private int solve(int[] minNums, int[] bigNums, int dif) {
        Arrays.sort (minNums);
        Arrays.sort (bigNums);
        int n = minNums.length, m = bigNums.length;
        if (n * 6 < m) {
            return -1;
        }
        int minIdx = 0, bigIdx = m - 1;
        int res = 0;
        for (; minIdx < n && bigIdx >= 0 && dif > 0; ) {
            if (6 - minNums[minIdx] >= bigNums[bigIdx] - 1) {
                dif -= 6 - minNums[minIdx];
                res += minNums[minIdx++] != 6 ? 1 : 0;
            } else {
                dif -= bigNums[bigIdx] - 1;
                res += bigNums[bigIdx--] != 1 ? 1 : 0;

            }
        }
        while (minIdx < n && dif > 0) {
            dif -= 6 - minNums[minIdx];
            res += minNums[minIdx++] != 6 ? 1 : 0;
        }
        while (bigIdx >= 0 && dif > 0) {
            dif -= bigNums[bigIdx] - 1;
            res += bigNums[bigIdx--] != 1 ? 1 : 0;

        }
        return res;
    }

    @Test
    public void test() {
        int dif = 5;
        for (int i = 1; i < 10; i++) {
            System.out.println ((i + dif - 1) / i);
        }

        int[] nums1 = {1, 1, 1, 1, 1, 1, 1}, nums2 = {6};
        System.out.println (minOperations (nums1, nums2));
    }
}
