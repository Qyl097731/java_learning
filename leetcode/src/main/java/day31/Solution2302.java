package day31;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/10/23 21:39
 * @author: qyl
 */
public class Solution2302 {
    @Test
    public void test() {
        System.out.println(countSubarrays(new int[]{2, 1, 4, 3, 5}, 10));
    }

    public long countSubarrays(int[] nums, long k) {
        int n = nums.length;
        long res = 0;
        int l = 0, r = 0;
        long sum = 0;
        while (r < n) {
            sum += nums[r];
            while(l <= r && sum * (r-l+1) >= k){
                sum-=nums[l++];
            }
            res += r-l+1;
            r++;
        }
        return res;
    }
}
