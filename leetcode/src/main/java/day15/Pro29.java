package day15;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.Vector;

/**
 * @description
 * @date:2022/10/5 8:15
 * @author: qyl
 */
public class Pro29 {
    public static void main(String[] args) {
        System.out.println(new Solution29().divide(2147483647,
                3));
    }
}

class Solution29 {
    public int divide(int dividend, int divisor) {
        if (dividend == Integer.MIN_VALUE) {
            if (divisor == -1) {
                return Integer.MAX_VALUE;
            }
            if (divisor == 1) {
                return Integer.MIN_VALUE;
            }
        }
        if (divisor == Integer.MIN_VALUE) {
            return dividend == Integer.MIN_VALUE ? 1 : 0;
        }
        boolean flag = dividend > 0 == divisor > 0;
        dividend = -Math.abs(dividend);
        divisor = -Math.abs(divisor);
        Stack<Integer> nums = new Stack<>();
        nums.push(divisor);
        while (nums.peek() >= dividend - nums.peek()) {
            nums.push(nums.peek() + nums.peek());
        }
        int ans = 0;
        for (int i = nums.size() - 1; i >= 0; i--) {
            if (dividend <= nums.peek()) {
                dividend -= nums.peek();
                ans += (1 << i);
            }
            nums.pop();
        }
        return flag ? ans : -1 * ans;
    }
}
