package templates;

/**
 * @description
 * @date:2022/11/20 22:52
 * @author: qyl
 */
public class BinarySearch {
    int find(int num, int[] nums) {
        int ans = -1;
        int l = 0, r = nums.length - 1, mid;
        while (l <= r) {
            mid = l + r >> 1;
            if (check (nums[mid])) {
                ans = mid;
                l = mid - 1;
            } else {
                r = mid + 1;
            }
        }
        return ans;
    }

    private boolean check(int num) {
        return false;
    }
}
