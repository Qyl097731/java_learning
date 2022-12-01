package daily;

/**
 * @description
 * @date:2022/12/1 15:01
 * @author: qyl
 */
public class Solution1779 {
    public int nearestValidPoint(int x, int y, int[][] points) {
        int[] res = {Integer.MAX_VALUE, -1};
        int n = points.length;
        for (int i = 0; i < n; i++) {
            int[] point = points[i];
            int px = point[0], py = point[1];
            if (px == x || py == y) {
                int dis = Math.abs (px - x) + Math.abs (py - y);
                if (dis < res[0]) {
                    res = new int[]{dis, i};
                }
            }
        }
        return res[1];
    }
}
