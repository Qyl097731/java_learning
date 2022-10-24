package day32;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/10/24 7:46
 * @author: qyl
 */
public class Solution915 {
    public int partitionDisjoint(int[] nums) {
        int n = nums.length;
        int[] post = new int[n + 1];
        post[n] = Integer.MAX_VALUE;
        for (int i = n - 1; i >= 0; i--) {
            post[i] = Math.min(post[i + 1], nums[i]);
        }
        int maxNum = nums[0];
        for (int i = 1; i <= n; i++) {
            maxNum = Math.max(maxNum,nums[i-1]);
            if (maxNum <= post[i]){
                return i;
            }
        }
        return 0;
    }

    @Test
    public void test() {
        System.out.println(partitionDisjoint(new int[]{0, 0}));
    }
}
