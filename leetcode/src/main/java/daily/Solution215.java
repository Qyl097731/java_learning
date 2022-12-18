package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/18 17:20
 * @author: qyl
 */
public class Solution215 {
    public int findKthLargest(int[] nums, int k) {
        int n = nums.length;
        int[] cnt = new int[100005];
        for (int i = 0; i < n; i++) {
            cnt[nums[i] + 10000]++;
        }
        for (int i = cnt.length - 2; i >= 0; i--) {
            cnt[i] += cnt[i + 1];
            if (cnt[i] >= k) {
                return i - 10000;
            }
        }
        return -1;
    }

    @Test
    public void test() {
        findKthLargest (new int[]{1, 2, 3, 4, 5}, 2);
    }
}
