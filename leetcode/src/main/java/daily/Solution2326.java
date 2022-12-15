package daily;

import common.ListNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/15 23:25
 * @author: qyl
 */
public class Solution2326 {
    int[][] dir = {{0, 1}, {1, 0}, {0, -1}, {-1, 0}};

    public int[][] spiralMatrix(int m, int n, ListNode head) {
        int[][] res = new int[m][n];
        for (int i = 0; i < m; i++) {
            Arrays.fill (res[i], -1);
        }
        int x = 0, y = 0, cur = 0;
        while (head != null) {
            int val = head.val;
            if (x >= 0 && x < m && y >= 0 && y < n && res[x][y] == -1) {
                res[x][y] = val;
            }
            int xx = x + dir[cur][0], yy = y + dir[cur][1];
            if (!(xx >= 0 && xx < m && yy >= 0 && yy < n && res[xx][yy] == -1)) {
                cur = (cur + 1) % 4;
            }
            x = x + dir[cur][0];
            y = y + dir[cur][1];
            head = head.next;
        }
        return res;
    }

    @Test
    public void test() {
        ListNode hea = ListNode.createList (new int[]{3, 0, 2, 6, 8, 1, 7, 9, 4, 2, 5, 5, 0});
        spiralMatrix (3, 5, hea);
    }
}
