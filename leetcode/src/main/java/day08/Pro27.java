package day08;

/**
 * @author qyl
 * @program Pro27.java
 * @Description 移除元素
 * @createTime 2022-09-01 16:11
 */
public class Pro27 {
    public static void main(String[] args) {
        System.out.println(new Solution27()
                .removeElement(new int[]{3, 2, 2, 3}, 3));
    }
}

class Solution27 {
    public int removeElement(int[] nums, int val) {
        int idx = 0;
        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != val){
                nums[idx++] = nums[i];
            }
        }
        return idx;
    }
}