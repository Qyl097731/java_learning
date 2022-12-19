package daily;

/**
 * @description
 * @date:2022/12/19 16:50
 * @author: qyl
 */
public class Offer53l {
    public int search(int[] nums, int target) {
//        return Arrays.stream (nums).reduce (0, (i, num) -> i + (num == target ? 1 : 0));
//        return (int) Arrays.stream (nums).filter (num -> num == target).count ( );
//        return solve1(nums,target);
        return solve2 (nums, target);
    }

    private int solve2(int[] nums, int target) {
        return find (nums, target + 1) - find (nums, target);
    }

    private int find(int[] nums, int target) {
        int l = 0, r = nums.length - 1, mid;
        int res = 0;

        while (l <= r) {
            mid = (l + r + 1) >> 1;
            if (nums[mid] >= target) {
                r = mid - 1;
                res = mid;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }

    private int solve1(int[] nums, int target) {
        int cnt = 0;
        for (int num : nums) {
            cnt += (num == target ? 1 : 0);
        }
        return cnt;
    }
}
