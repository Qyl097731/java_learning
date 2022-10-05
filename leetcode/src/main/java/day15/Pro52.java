package day15;

/**
 * @description
 * @date:2022/10/5 9:16
 * @author: qyl
 */
public class Pro52 {
    public static void main(String[] args) {
        System.out.println(new Solution52().totalNQueens(4));
    }
}

class Solution52 {
    boolean[] used;
    boolean[][] diagonal;
    int res = 0;
    public int totalNQueens(int n) {
        used = new boolean[n];
        diagonal = new boolean[n * 2][2];
        dfs(0, n);
        return res;
    }

    private void dfs(int row, int target) {
        if (row == target) {
            res++;
            return;
        }

        for (int i = 0; i < target; i++) {
            if (!used[i] && !diagonal[row + i][0] && !diagonal[row - i + target][1]) {
                used[i] = diagonal[row + i][0] = diagonal[row - i + target][1] = true;
                 dfs(row + 1, target);
                used[i] = diagonal[row + i][0] = diagonal[row - i + target][1] = false;
            }
        }
    }
}
