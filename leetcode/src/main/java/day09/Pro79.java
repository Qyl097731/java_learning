package day09;

/**
 * @description 单词搜索
 * @date:2022/9/3 21:21
 * @author: qyl
 */
public class Pro79 {
    public static void main(String[] args) {
        System.out.println(new Solution79().exist(new char[][]{{'A','B','C','E'},{'S','F','C','S'},{'A','D','E','E'}}
                , "ABCB"));
    }
}

class Solution79 {
    int[][] dir = new int[][]{{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    boolean[][] use ;
    int n, m;

    public boolean exist(char[][] board, String word) {
        StringBuilder sb = new StringBuilder();
        if (board == null) {
            return false;
        }
        n = board.length;
        m = board[0].length;
        use = new boolean[n][m];
        boolean flag = false;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (board[i][j] == word.charAt(0)) {
                    sb.append(board[i][j]);
                    use[i][j] = true;
                    flag = dfs(board, word, sb, i, j, 1);
                    use[i][j] = false;
                    sb.deleteCharAt(0);
                    if (flag) {
                        return flag;
                    }
                }
            }
        }
        return flag;
    }

    private boolean dfs(char[][] board, String word, StringBuilder sb, int x, int y, int len) {
        if (sb.toString().equals(word)) {
            return true;
        }
        if (len >= word.length()) {
            return false;
        }
        for (int i = 0; i < 4; i++) {
            int tempx = x + dir[i][0], tempy = y + dir[i][1];
            if (tempx >= 0 && tempx < n && tempy >= 0 && tempy < m && word.charAt(len) == board[tempx][tempy] && !use[tempx][tempy]) {
                sb.append(word.charAt(len));
                use[tempx][tempy] = true;
                if (dfs(board, word, sb, tempx, tempy, len + 1)) {
                    return true;
                }
                use[tempx][tempy] = false;
                sb.deleteCharAt(len);
            }
        }
        return false;
    }

}
