package daily;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2023/2/1 0:21
 * @author: qyl
 */
public class Solution1 {
    public int[] twoSum(int[] nums, int target) {
        if (nums != null) {
            Integer[] index = IntStream.range (0, nums.length).boxed ().toArray (Integer[]::new);
            Arrays.sort (index, (i, j) -> Integer.compare (nums[i], nums[j]));
            int l = 0, r = nums.length - 1;
            while (l < r) {
                if (nums[index[l]] + nums[index[r]] == target) {
                    return new int[]{index[l], index[r]};
                } else if (nums[index[l]] + nums[index[r]] > target) {
                    r--;
                } else {
                    l++;
                }
            }
        }
        return null;
    }
}
