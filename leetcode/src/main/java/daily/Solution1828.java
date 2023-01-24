package daily;

/**
 * @description
 * @date:2023/1/24 23:19
 * @author: qyl
 */
public class Solution1828 {
    public int[] countPoints(int[][] points, int[][] queries) {
        int m = queries.length, n = points.length;
        int[] res = new int[m];
        for (int i = 0; i < m; i++) {
            int[] q = queries[i];
            double x = q[0], y = q[1], r = q[2];
            for (int[] p : points) {
                if (getDis (p[0], p[1], x, y) <= r) {
                    res[i]++;
                }
            }
        }
        return res;
    }

    private double getDis(double x, double y, double x1, double y1) {
        return Math.sqrt (Math.pow (x - x1, 2) + Math.pow (y - y1, 2));
    }
}
