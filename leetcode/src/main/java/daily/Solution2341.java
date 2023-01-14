package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/13 22:32
 * @author: qyl
 */
public class Solution2341 {
    public int[] numberOfPairs(int[] nums) {
        int n = nums.length;
        int[] res = new int[]{0, n};
        int[] used = new int[n];
        Arrays.fill (used, 0);
        boolean flag = true;
        while (flag) {
            flag = false;
            for (int i = 0; i < n && !flag; i++) {
                if (used[i] == 0) {
                    for (int j = 0; j < n && !flag; j++) {
                        if (used[j] == 0 && nums[j] == nums[i] && i != j) {
                            used[i] = 1;
                            used[j] = 1;
                            flag = true;
                        }
                    }
                }
            }
            if (flag) {
                res[0]++;
                res[1] -= 2;
            }
        }
        return res;
    }

    @Test
    public void test() {
        int[] nums = {1, 3, 2, 1, 3, 2, 2};
        numberOfPairs (nums);
    }
}
