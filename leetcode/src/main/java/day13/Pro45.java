package day13;

/**
 * @description
 * @date:2022/10/2 17:08
 * @author: qyl
 */
public class Pro45 {
    public static void main(String[] args) {
        System.out.println(new Solution45().jump(new int[]{4,3,0,1,4}));
    }
}

class Solution45 {
    public int jump(int[] nums) {
        int len = nums.length;
        if (len == 1) {
            return 0;
        }

        int res = 1,mx = nums[0];
        for (int i = 0; i < len; ) {
            if (mx < len - 1){
                res++;
            }else{
                break;
            }
            int step = nums[i], idx = i;
            for (int j = 1; j <= step; j++) {
                if (mx <= nums[i + j] + i + j) {
                    mx = nums[i + j] + j + i;
                    idx = i + j;
                }
            }
            i = idx;
        }
        return res;
    }
}
