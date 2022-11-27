package day53;

import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/11/27 10:29
 * @author: qyl
 */
public class Solution6248 {
    public int countSubarrays(int[] nums, int k) {
        int n = nums.length, i, j = 0;
        int[] s = new int[n + 1];
        for (i = 0; i < n; i++) {
            if (nums[i] > k) {
                s[i + 1] = s[i] + 1;
            } else if (nums[i] < k) {
                s[i + 1] = s[i] - 1;
            } else {
                j = i;
                s[i + 1] = s[i];
            }
        }
        Map<Integer, Integer> t = new HashMap<> ( );
        for (i = 0; i <= j; i++) {
            int num = s[i];
            t.put (num, t.getOrDefault (num, 0) + 1);
        }
        int ans = 0;
        for (; i <= n; i++) {
            int cnt1 = t.getOrDefault (s[i], 0);
            int cnt2 = t.getOrDefault (s[i] - 1, 0);
            ans += cnt1 + cnt2;
        }
        return ans;
    }

    @Test
    public void test() {
        int[] nums = {3, 2, 1, 4, 5};
        System.out.println (countSubarrays (nums, 4));
        nums = new int[]{2, 3, 1};
        System.out.println (countSubarrays (nums, 3));

        int[] nums1 = {2, 1, 4, 5, 6, 7};
        System.out.println (countSubarrays (nums1, 4));


        int[] nums2 = {3, 2, 1, 4, 5, 6, 7};
        System.out.println (countSubarrays (nums2, 4));
        int[] nums3 = {2, 5, 1, 4, 3, 6};
        System.out.println (countSubarrays (nums3, 1));

        int[] nums4 = {5, 19, 11, 15, 13, 16, 4, 6, 2, 7, 10, 8, 18, 20, 1, 3, 17, 9, 12, 14};
        System.out.println (countSubarrays (nums4, 6));

    }
}
