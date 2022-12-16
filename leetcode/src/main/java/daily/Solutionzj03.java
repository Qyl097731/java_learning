package daily;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/12/16 17:46
 * @author: qyl
 */
public class Solutionzj03 {
    public int buildTransferStation(int[][] area) {
        int n = area.length, m = area[0].length;
        List<int[]> post = new ArrayList<> ( );
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                if (area[i][j] == 1) {
                    post.add (new int[]{i, j});
                }
            }
        }
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < m; j++) {
                int temp = 0;
                for (int[] ints : post) {
                    temp += Math.abs (ints[0] - i) + Math.abs (ints[1] - j);
                }
                res = Math.min (res, temp);
            }
        }
        return res;
    }
}
