package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * @description
 * @date 2023/2/8 18:34
 * @author: qyl
 */
public class Solution2250 {
    int[][] rec;
    int m;

    int[] sum = new int[101];

    public int[] countRectangles(int[][] rectangles, int[][] points) {
//        return solve1(rectangles,points);
//        return solve2 (rectangles, points);
        return solve3 (rectangles, points);
    }

    private int[] solve3(int[][] rectangles, int[][] points) {
        m = rectangles.length;
        int n = points.length;
        int[] res = new int[n];
        Integer[] ids = IntStream.range (0, n).boxed ().toArray (Integer[]::new);
        Arrays.sort (rectangles, (a, b) -> b[0] - a[0]);
        rec = rectangles;
        Arrays.sort (ids, (i, j) -> points[j][0] - points[i][0]);

        int i = 0;
        for (int realId : ids) {
            int[] p = points[realId];
            while (i < m && rec[i][0] >= p[0]) {
                update (rec[i][1]);
                i++;
            }
            res[realId] = i - query (p[1] - 1);
        }
        return res;
    }

    private int lowbit(int x) {
        return x & (-x);
    }

    private int query(int x) {
        int res = 0;
        while (x > 0) {
            res += sum[x];
            x -= lowbit (x);
        }
        return res;
    }

    private void update(int x) {
        while (x < 101) {
            sum[x]++;
            x += lowbit (x);
        }
    }

    private int[] solve2(int[][] rectangles, int[][] points) {
        int n = points.length;
        m = rectangles.length;
        Arrays.sort (rectangles, (a, b) -> b[1] - a[1]);
        rec = rectangles;
        Integer[] ids = IntStream.range (0, n).boxed ().toArray (Integer[]::new);
        Arrays.sort (ids, (i, j) -> points[j][1] - points[i][1]);
        List<Integer> xList = new ArrayList<> ();
        int[] res = new int[n];
        int j = 0;
        for (int readId : ids) {
            int[] p = points[readId];
            int last = j;
            while (j < m && rec[j][1] >= p[1]) {
                xList.add (rec[j++][0]);
            }
            if (last != j) {
                Collections.sort (xList, (a, b) -> b - a);
            }
            res[readId] = binarySearch (p[0], xList);
        }
        return res;
    }

    private int binarySearch(int x, List<Integer> xList) {
        int l = 0, r = m - 1, mid, res = 0;
        while (l <= r) {
            mid = (l + r) / 2;
            if (xList.get (mid) >= x) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    private int[] solve1(int[][] rectangles, int[][] points) {
        this.m = rectangles.length;
        rec = new int[m][2];
        Arrays.sort (rectangles, (a, b) -> Integer.compare (b[0], a[0]));
        this.rec = rectangles;

        int n = points.length;
        Integer[] ids = IntStream.range (0, n).boxed ().toArray (Integer[]::new);
        Arrays.sort (ids, (i, j) -> points[j][0] - points[i][0]);
        int[] cnt = new int[101];
        int[] res = new int[n];
        int i = 0;
        for (int realId : ids) {
            int[] p = points[realId];
            while (i < m && rec[i][0] >= p[0]) {
                cnt[rec[i++][1]]++;
            }
            for (int j = p[1]; j < 101; j++) {
                res[realId] += cnt[j];
            }
        }
        return res;
    }

    @Test
    public void test() {
        countRectangles (new int[][]{{7, 1}, {2, 6}, {1, 4}, {5, 2}, {10, 3}, {2, 4}, {5, 9}}, new int[][]{{10, 3}, {8, 10}, {2, 3}, {5, 4}, {8, 5}, {7, 10}, {6, 6}, {3, 6}});
    }

}
