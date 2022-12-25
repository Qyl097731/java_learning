package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/25 10:42
 * @author: qyl
 */
public class Solution6270 {
    public int takeCharacters(String s, int k) {
        int n = s.length ( );
        if (k == 0) return 0;
        int[] nums = new int[]{k, k, k};
        int[][] post = new int[3][n + 1];
        for (int i = n - 1; i >= 0; i--) {
            for (int j = 0; j < 3; j++) {
                post[j][i] = post[j][i + 1];
            }
            post[s.charAt (i) - 'a'][i]++;
        }
        if (post[0][0] < k || post[1][0] < k || post[2][0] < k) return -1;
        int res = 0;
        for (int i = n - 1; i >= 0; i--) {
            nums[s.charAt (i) - 'a']--;
            if (nums[0] <= 0 && nums[1] <= 0 && nums[2] <= 0) {
                res = n - i;
                break;
            }
        }
        nums = new int[]{k, k, k};

        for (int i = 0; i < n; i++) {
            nums[s.charAt (i) - 'a']--;
            if (nums[0] <= 0 && nums[1] <= 0 && nums[2] <= 0) {
                res = Math.min (res, i + 1);
                break;
            }
        }
        nums = new int[]{k, k, k};
        for (int i = 0; i < n; i++) {
            nums[s.charAt (i) - 'a']--;
            int l = i + 1, r = n - 1, mid;
            while (l <= r) {
                mid = (l + r) / 2;
                if (nums[0] - post[0][mid] <= 0 && nums[1] - post[1][mid] <= 0 && nums[2] - post[2][mid] <= 0) {
                    res = Math.min (res, i + 1 + n - mid);
                    l = mid + 1;
                } else {
                    r = mid - 1;
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        takeCharacters ("acbcc", 1);
    }
}
