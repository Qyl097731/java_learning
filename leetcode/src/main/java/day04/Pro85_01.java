package day04;

import java.util.Stack;

/**
 * @description 最大矩形
 * @date:2022/8/27 23:26
 * @author: qyl
 */
public class Pro85_01 {
    public static void main(String[] args) {
        System.out.println(new Solution75_01().maximalRectangle(new char[][]{{'1', '0', '1', '0', '0'}, {'1', '0', '1', '1', '1'}, {'1', '1', '1', '1', '1'}, {
                '1', '0', '0', '1', '0'}}));
    }
}

class Solution75_01 {
    int n, m;
    int res = 0, temp = 0, width = 0;

    public int maximalRectangle(char[][] matrix) {
        if (matrix == null) {
            return 0;
        }
        n = matrix.length;
        m = matrix[0].length;

        int[][] left = new int[n][m];
        int[][] right = new int[n][m];
        int[][] height = new int[n][m];
        for (int j = 0; j < m; j++) {
            for (int i = 0; i < n; i++) {
                if (matrix[i][j] == '0') {
                    height[i][j] = 0;
                } else {
                    height[i][j] = i >= 1 ? height[i - 1][j] + 1 : 1;
                }
            }
        }

        Stack<int[]> stack = new Stack<>();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                while (!stack.isEmpty() && height[i][j] <= stack.peek()[0]) {
                    stack.pop();
                }
                if (stack.isEmpty()){
                    left[i][j] = -1;
                }else{
                    left[i][j] = stack.peek()[1];
                }
                stack.push(new int[]{height[i][j],j});

            }
            stack.clear();
        }

        for (int i = 0; i < n; i++) {
            for (int j = m-1; j >= 0 ; j--) {
                while (!stack.isEmpty() && height[i][j] <= stack.peek()[0]) {
                    stack.pop();
                }
                if (stack.isEmpty()){
                    right[i][j] = m;
                }else{
                    right[i][j] = stack.peek()[1];
                }
                stack.push(new int[]{height[i][j],j});
            }
            stack.clear();
        }

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                res = Math.max(res,height[i][j] * (right[i][j] - left[i][j] - 1 ));
            }
        }
        return res;
    }
}
