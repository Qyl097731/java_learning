package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/17 14:41
 * @author: qyl
 */
public class Solution1764 {
    public boolean canChoose(int[][] groups, int[] nums) {
        int n = groups.length;
        int len = nums.length;
        int l = 0;
        for (int i = 0; i < n; i++) {
            int r = l + groups[i].length;
            while (r <= len) {
                if (Arrays.equals (Arrays.copyOfRange (nums, l, r), groups[i])) {
                    l = r;
                    break;
                } else {
                    l++;
                    r++;
                }
            }
            if (r > len) {
                return false;
            }
        }
        return true;
    }

    @Test
    public void test() {
        canChoose (new int[][]{{1, -1, -1}, {3, -2, 0}}, new int[]{1, -1, 0, 1, -1, -1, 3, -2, 0});
    }
}
