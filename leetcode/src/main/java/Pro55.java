package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 21:59
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro55 {
}
class Solution55 {
    public boolean canJump(int[] nums) {
        int maxIdx = 0;
        int len = nums.length;
        for(int i = 0 ; i < len;i++){
            if(i > maxIdx){
                return false;
            }
            maxIdx = Math.max(maxIdx,i+nums[i]);
            if(maxIdx >= len-1){
                return true;
            }
        }
        return true;
    }
}
