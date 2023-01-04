package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/4 22:00
 * @author: qyl
 */
public class Offer12 {
    boolean res = false;
    int[][] dir = new int[][]{{-1, 0}, {1, 0}, {0, 1}, {0, -1}};

    public boolean exist(char[][] board, String word) {
        int n = board.length, m = board[0].length;
        boolean[][] vis = new boolean[n][m];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (res) {
                    return res;
                }
                if (board[i][j] == word.charAt (0)) {
                    vis[i][j] = true;
                    dfs (board, vis, n, m, word, i, j, 1);
                    vis[i][j] = false;
                }
            }
        }
        return res;
    }

    private void dfs(char[][] board, boolean[][] vis, int n, int m, String word, int x, int y, int len) {

        if (res) return;

        if (len == word.length ()) {
            res = true;
            return;
        }
        if (word.charAt (len - 1) != board[x][y]) {
            return;
        }
        for (int i = 0; i < 4; i++) {
            int xx = x + dir[i][0], yy = y + dir[i][1];
            if (xx < n && xx >= 0 && yy < m && yy >= 0 && !vis[xx][yy] && !res) {
                vis[xx][yy] = true;
                dfs (board, vis, n, m, word, xx, yy, len + 1);
                vis[xx][yy] = false;
            }
        }
    }

    @Test
    public void test() {
        char[][] board = {{'A', 'B', 'C', 'E'}, {'S', 'F', 'C', 'S'}, {'A', 'D', 'E', 'E'}};
        exist (board, "ABCCED");
    }
}
