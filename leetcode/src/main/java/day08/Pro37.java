package day08;

/**
 * @author qyl
 * @program Pro37.java
 * @Description 解数独
 * @createTime 2022-09-01 16:33
 */
public class Pro37 {
    public static void main(String[] args) {
        new Solution37().solveSudoku(new char[][]{{'5', '3', '.', '.', '7', '.', '.', '.', '.'}, {'6', '.', '.', '1', '9', '5', '.', '.', '.'}, {'.', '9', '8', '.', '.', '.', '.', '6', '.'}, {'8', '.', '.', '.', '6', '.', '.', '.', '3'}, {'4', '.', '.', '8', '.', '3', '.', '.', '1'}, {'7', '.', '.', '.', '2', '.', '.', '.', '6'}, {'.', '6', '.', '.', '.', '.', '2', '8', '.'}, {'.', '.', '.', '4', '1', '9', '.', '.', '5'}, {'.', '.', '.', '.', '8', '.', '.', '7', '9'}});
    }
}

class Solution37 {
    boolean[][] row, line;
    boolean[][][] matrix;
    int m, n;
    boolean success;
    int total;

    public void solveSudoku(char[][] board) {
        m = board[0].length;
        n = board.length;
        matrix = new boolean[n / 3][m / 3][10];
        row = new boolean[n][10];
        line = new boolean[m][10];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == '.') {
                    total++;
                    continue;
                }
                row[i][board[i][j] - '0'] = true;
                line[j][board[i][j] - '0'] = true;
                matrix[i / 3][j / 3][board[i][j] - '0'] = true;
            }
        }
        dfs(board, total);
    }

    void dfs(char[][] board, int total) {
        if (success) {
            return;
        }
        if (total == 0) {
            success = true;
            return;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                boolean flag = false;
                if (board[i][j] == '.') {
                    for (int k = 1; k <= 9; k++) {
                        if (check(i, j, k)) {
                            board[i][j] = (char) ('0' + k);
                            row[i][k] = line[j][k] = matrix[i / 3][j / 3][k] = true;
                            dfs(board, total - 1);
                            if (success){
                                return;
                            }
                            row[i][k] = line[j][k] = matrix[i / 3][j / 3][k] = false;
                            board[i][j] = '.';
                        }
                        if (k == 9){
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    return;
                }
            }
        }

    }

    boolean check(int i, int j, int num) {
        return !row[i][num] && !line[j][num] && !matrix[i / 3][j / 3][num];
    }
}