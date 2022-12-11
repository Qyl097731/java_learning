package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/11 22:04
 * @author: qyl
 */
public class Solution6257 {
    public int deleteGreatestValue(int[][] grid) {
        int n = grid.length;
        int m = grid[0].length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            Arrays.sort (grid[i]);
        }
        for (int j = 0; j < m; j++) {
            int temp = Integer.MIN_VALUE;
            for (int i = 0; i < n; i++) {
                temp = Math.max (grid[i][j], temp);
            }
            res += temp;
        }
        return res;
    }

    @Test
    public void test() {
    }
}
