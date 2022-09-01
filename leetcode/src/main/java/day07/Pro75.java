package day07;

import java.util.Arrays;

/**
 * @author qyl
 * @program Pro75.java
 * @Description 颜色分类
 * @createTime 2022-09-01 13:22
 */
public class Pro75 {
    public static void main(String[] args) {
        new Solution75().sortColors(new int[]{2,0,2,1,1,0});

    }
}

class Solution75 {
    public void sortColors(int[] nums) {
        int len = nums.length;
        int[] cnt = new int[3];
        for (int i = 0; i < len; i++) {
            cnt[nums[i]]++;
        }
        for (int i = 0 , idx = 0; i < 3; i++) {
            while(cnt[i]>0){
                nums[idx++] = i;
                cnt[i]--;
            }
        }
    }
}