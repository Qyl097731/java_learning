package daily;

/**
 * @description
 * @date:2023/1/1 19:17
 * @author: qyl
 */
public class Offer21 {
    public int[] exchange(int[] nums) {
//        return solve1(nums);
        return solve2 (nums);
    }

    private int[] solve2(int[] nums) {
        int n = nums.length;
        int l = 0, r = n - 1;
        while (l <= r) {
            while (l <= r && (nums[l] & 1) == 1) {
                l++;
            }

            while (l <= r && (nums[r] & 1) == 0) {
                r--;
            }
            if (l > r) {
                break;
            }
            int temp = nums[r];
            nums[r] = nums[l];
            nums[l] = temp;
        }
        return nums;
    }

    private int[] solve1(int[] nums) {
        int n = nums.length;
        int cnt = 0;
        int[] res = new int[n];
        for (int i = 0; i < n; i++) {
            if ((nums[i] & 1) == 1)
                res[cnt++] = nums[i];
        }
        for (int i = 0; i < n; i++) {
            if ((nums[i] & 1) == 0)
                res[cnt++] = nums[i];
        }
        return res;
    }
}
