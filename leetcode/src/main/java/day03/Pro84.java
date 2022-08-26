package day03;

import java.util.Stack;

/**
 * @author qyl
 * @program Pro84.java
 * @Description 柱状图中最大的矩形 单调栈
 * @createTime 2022-08-26 10:41
 */
public class Pro84 {
    public static void main(String[] args) {
        System.out.println(new Solution84().largestRectangleArea(new int[]{2, 1, 5, 6, 2, 3}));
    }
}

class Solution84 {
    public int largestRectangleArea(int[] heights) {
        int res = 0;
        int len = heights.length;
        int[] left = new int[len];
        int[] right = new int[len];
        Stack<Integer> stack = new Stack<>();
        for (int i = 0; i < len; i++) {
            if (!stack.isEmpty()) {
                while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                    stack.pop();
                }
            }
            if (!stack.isEmpty()) {
                left[i] = stack.peek();
            } else {
                left[i] = -1;
            }
            stack.push(i);
        }
        stack.removeAllElements();
        for (int i = len - 1; i >= 0; i--) {
            if (!stack.isEmpty()) {
                while (!stack.isEmpty() && heights[stack.peek()] >= heights[i]) {
                    stack.pop();
                }
            }
            if (!stack.isEmpty()) {
                right[i] = stack.peek();
            } else {
                right[i] = len;
            }
            stack.push(i);
        }

        for (int i = 0; i < len; i++) {
            res = Math.max((right[i] - left[i] - 1) * heights[i],res);
        }

        return res;
    }
}
