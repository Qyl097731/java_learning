package day11;

/**
 * @description
 * @date:2022/9/28 12:44
 * @author: qyl
 */
public class Pro35 {
    public static void main(String[] args) {
        System.out.println(new Solution35().searchInsert(new int[]{1,3,5,6},3));
    }
}
class Solution35 {
    public int searchInsert(int[] nums, int target) {
        int l = 0 , r  = nums.length-1;
        int mid ;
        while(l <= r){
            mid = l+r>>1;
            if (nums[mid] == target){
                return mid;
            }else if(nums[mid] < target){
                l = mid+1;
            }else{
                r = mid-1;
            }
        }
        return l;
    }
}
