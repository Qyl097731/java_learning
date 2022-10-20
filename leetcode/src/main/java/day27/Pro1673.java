package day27;

import com.sun.jmx.remote.internal.ArrayQueue;

import java.util.*;

/**
 * @description
 * @date:2022/10/19 23:53
 * @author: qyl
 */
public class Pro1673 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution1673().mostCompetitive(new int[]{71,18,52,29,55,73,24,42,66,8,
                80,2}, 3)));
    }
}
class Solution1673 {
    public int[] mostCompetitive(int[] nums, int k) {
        int cur = k - 1, n = nums.length;
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            int num = nums[i];
            while (!stack.isEmpty() && stack.peek() > num && stack.size() + n - i - 1>= k) {
                stack.pop();
            }
            stack.push(num);
        }
        int[] res = new int[k];
        while (!stack.empty() && stack.size() > k){
            stack.pop();
        }
        while(!stack.empty()){
            res[cur--] = stack.pop();
        }
        return res;
    }
}
