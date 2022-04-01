package leetcode;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 17:04
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro42 {
    public static void main(String[] args) {
        int trap = new Solution42().trap(new int[]{0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1});
        System.out.println(trap);
    }
}
class Solution42 {
    public int trap(int[] height) {
        int left = 0, right = height.length-1;
        int lMax = height[left], rMax = height[right] ;
        int ans = 0;
        while(left < right){

            if(height[left] <= height[right]){
                if(lMax <= height[left]){
                    lMax = height[left];
                }else{

                    ans += lMax - height[left];
                }
                left++;
            }else{
                if(rMax <= height[right]){
                    rMax = height[right];
                }else{
                    ans += rMax - height[right];
                }
                right--;
            }
        }
        return ans;
    }
}
