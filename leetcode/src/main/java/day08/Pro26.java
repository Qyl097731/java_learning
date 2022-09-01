package day08;

/**
 * @author qyl
 * @program Pro26.java
 * @Description TODO
 * @createTime 2022-09-01 16:02
 */
public class Pro26 {
    public static void main(String[] args) {
        System.out.println(new Solution26().removeDuplicates(new int[]{0, 0, 1, 1, 1, 2, 2, 3, 3, 4}));
    }
}
class Solution26 {
    public int removeDuplicates(int[] nums) {
        int len = nums.length,pre = 0, res = 0;
        if (len == 1){
            return 1;
        }
        pre = nums[0];
        for (int i = 1; i < len; i++) {
            if (nums[i] != pre){
                nums[++res] = nums[i];
                pre = nums[i];
            }
        }
        return res+1;
    }
}