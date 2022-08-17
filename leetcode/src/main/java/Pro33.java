package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 00:19
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro33 {
    public static void main(String[] args) {
        int i =0 ;
        System.out.println(i);
    }
}
class Solution33 {
    public int search(int[] nums, int target) {
        for(int i = 0 ; i < nums.length; i++){
            if(nums[i] == target){
                return i;
            }
        }
        return -1;
    }
}
