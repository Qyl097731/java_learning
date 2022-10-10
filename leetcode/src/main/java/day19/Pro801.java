package day19;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @description
 * @date:2022/10/10 0:09
 * @author: qyl
 */

class Solution801 {
    public int minSwap(int[] nums1, int[] nums2) {
        int len = nums1.length;
        int[][] dp = new int[len][2];

        dp[0][0] = 0;
        dp[0][1] = 1;
        for (int i = 1; i < len; i++) {
            if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1] && nums1[i] > nums2[i - 1] && nums2[i] > nums1[i - 1]) {
                dp[i][0] = Math.min(dp[i - 1][0], dp[i - 1][1]);
                dp[i][1] = Math.min(dp[i - 1][0], dp[i - 1][1]) + 1;
            } else if (nums1[i] > nums1[i - 1] && nums2[i] > nums2[i - 1]) {
                dp[i][0] = dp[i - 1][0];
                dp[i][1] = dp[i - 1][1] + 1;
            } else {
                dp[i][0] = dp[i - 1][1];
                dp[i][1] = dp[i - 1][0] + 1;
            }
        }
        return Math.min(dp[len-1][1], dp[len-1][0]);
    }

    @Test
    public void test1() {
        int res = minSwap(new int[]{1, 3, 5, 4}, new int[]{1, 2, 3, 7});
        Assertions.assertEquals(res, 1);
    }
}
