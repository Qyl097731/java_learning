package daily;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date 2023/2/8 18:34
 * @author: qyl
 */
public class Solution2249 {
    public int countLatticePoints(int[][] circles) {
        Set<Integer> points = new HashSet<> ();
        for (int[] circle : circles) {
            int x = circle[0], y = circle[1], r = circle[2];
            for (int i = x - r; i <= x + r; i++) {
                for (int j = y - r; j <= y + r; j++) {
                    if ((i - x) * (i - x) + (j - y) * (j - y) <= r * r) {
                        points.add (i * 1000 + j);
                    }
                }
            }
        }
        return points.size ();
    }

    @Test
    public void test() {
        countLatticePoints (new int[][]{{2, 2, 2}, {3, 4, 1}});
    }
}
