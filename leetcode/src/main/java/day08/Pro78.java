package day08;

import java.util.LinkedList;
import java.util.List;

/**
 * @author qyl
 * @program Pro78.java
 * @Description 子集
 * @createTime 2022-09-01 16:15
 */
public class Pro78 {
    public static void main(String[] args) {
        System.out.println(new Solution78().subsets(new int[]{1, 2, 3}));
    }
}
class Solution78 {
    public List<List<Integer>> subsets(int[] nums) {
        int len = nums.length, total = 1<<len;
        List<List<Integer>> res = new LinkedList<>();
        for (int i = 0; i < total; i++) {
            List<Integer> temp = new LinkedList<>();
            for (int j = 0; j < len; j++) {
                if (((1L<<j) & i) != 0){
                    temp.add(nums[j]);
                }
            }
            res.add(temp);
        }
        return res;
    }
}