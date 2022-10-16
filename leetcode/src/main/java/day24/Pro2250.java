package day24;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @description 非正解  内存会炸， 二维树状数组
 * @date:2022/10/15 22:40
 * @author: qyl
 */
public class Pro2250 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution2250().countRectangles(new int[][]{{1, 2}, {2, 3}, {2, 5}}, new int[][]{{2, 1}, {1, 4}})));
    }
}

class Solution2250 {
    Integer[] array;
    private final static int maxn =  1000 + 1;
    int[][] c = new int[maxn][maxn];
    int[] res;
    int n,m;

    private int found(int x) {
        int l = 0, r = n, mid;
        while (l < r) {
            mid = (l + r) >> 1;
            if (array[mid] >= x) {
                r = mid;
            } else {
                l = mid + 1;
            }
        }
        return r + 1;
    }


    public int[] countRectangles(int[][] rectangles, int[][] points) {
        n = rectangles.length; m = points.length;
        Set<Integer> set = new HashSet<>();
        res = new int[m];
        for (int i = 0; i < n; i++) {
            set.add(rectangles[i][0]);
        }
        array = set.toArray(new Integer[0]);
        for (int i = 0; i < n; i++) {
            rectangles[i][0] = found(rectangles[i][0]);
        }

        for (int i = 0; i < n; i++) {
            update(rectangles[i][0], rectangles[i][1]);
        }

        for (int i = 0; i < m; i++) {
            int id = found(points[i][0]), y = points[i][1];
            res[i] = sum(id-1,y - 1) + n - sum(id-1,y) - sum(id,y-1);
        }
        return res;
    }

    void update(int x, int y) {
        int yy = 0;
        while (x <= n) {
            yy = y;
            while (yy <= n) {
                c[x][yy]++;
                yy += lowbit(yy);
            }
            x += lowbit(x);
        }
    }

    int sum(int x, int y) {
        int ans = 0, yy = 0;
        while (x > 0) {
            yy = y;
            while (yy > 0) {
                ans += c[x][yy];
                yy -= lowbit(yy);
            }
            x -= lowbit(x);
        }
        return ans;
    }

    private int lowbit(int x) {
        return x & -x;
    }

}
