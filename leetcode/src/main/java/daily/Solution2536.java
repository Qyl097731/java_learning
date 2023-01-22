package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/22 17:52
 * @author: qyl
 */
public class Solution2536 {
    public int[][] rangeAddQueries(int n, int[][] queries) {
//        return solve1(n,queries);
//        return solve2(n,queries);
        return solve3 (n, queries);
    }

    private int[][] solve3(int n, int[][] queries) {
        int[][] m = new int[n][n];
        for (int[] query : queries) {
            for (int i = query[0]; i <= query[2]; i++) {
                m[i][query[1]]++;
                if (query[3] + 1 < n) {
                    m[i][query[3] + 1]--;
                }
            }
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                m[i][j] += m[i][j - 1];
            }
        }
        return m;
    }

    private int[][] solve2(int n, int[][] queries) {
        int[][] m = new int[n + 1][n + 1];
        for (int[] query : queries) {
            m[query[0]][query[1]]++;
            m[query[0]][query[3] + 1]--;
            m[query[2] + 1][query[1]]--;
            m[query[2] + 1][query[3] + 1]++;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 1; j < n; j++) {
                m[i][j] += m[i][j - 1];
            }
        }
        for (int i = 1; i < n; i++) {
            for (int j = 0; j < n; j++) {
                m[i][j] += m[i - 1][j];
            }
        }
        int[][] res = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                res[i][j] = m[i][j];
            }
        }
        return res;
    }

    private int[][] solve1(int n, int[][] queries) {
        int[][] matrix = new int[n][n];
        for (int[] query : queries) {
            for (int i = query[0]; i <= query[2]; i++) {
                for (int j = query[1]; j <= query[3]; j++) {
                    matrix[i][j]++;
                }
            }
        }
        return matrix;
    }

    @Test
    public void test() {
        rangeAddQueries (3, new int[][]{{1, 1, 2, 2}, {0, 0, 1, 1}});
    }
}
