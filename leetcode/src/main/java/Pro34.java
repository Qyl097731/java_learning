package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 01:07
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro34 {
    public static void main(String[] args) {
        int[] res = new Solution34().searchRange(new int[]{5,7,7,8,8,10},1);
        for(int i:res){
            System.out.println(i);
        }
    }
}
class Solution34 {
    public int[] searchRange(int[] nums, int target) {
        int l = 0 , r = nums.length-1;
        int lres = -1 , rres = -1;
        while(l <= r){
            int mid = (l+r)>>1;
            if(nums[mid] <= target){
                l = mid+1;
            }else{
                r = mid-1;
            }
        }

        if(r < 0 ||  nums[r] != target) {
            return new int[]{-1,-1};
        }

        rres = r;
        l = 0;r = nums.length-1;
        while(l <= r){
            int mid = (l+r)>>1;
            if(nums[mid] >= target){
                r = mid-1;
            }else{
                l = mid + 1;
            }
        }
        lres = l;
        return new int[]{lres,rres};
    }

}
