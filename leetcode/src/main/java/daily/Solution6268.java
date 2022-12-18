package daily;

/**
 * @description
 * @date:2022/12/18 17:30
 * @author: qyl
 */
public class Solution6268 {
    public int[] cycleLengthQueries(int n, int[][] queries) {
//        return solve1(n,queries);
        return solve2 (n, queries);
    }

    private int[] solve2(int n, int[][] queries) {
        int q = queries.length;
        int[] res = new int[q];
        for (int i = 0; i < q; i++) {
            int u = Math.max (queries[i][0], queries[i][1]),
                    v = Math.min (queries[i][0], queries[i][1]);
            int du = getDept (u), dv = getDept (v);
            res[i] += du + dv;
            while (du > dv) {
                u >>= 1;
                du--;
            }
            while (u != v) {
                u >>= 1;
                v >>= 1;
                du--;
            }
            res[i] -= du * 2;
        }
        return res;
    }

    private int getDept(int u) {
        for (int i = 30; i >= 0; i--) {
            if ((1 << i) <= u) {
                return i;
            }
        }
        return -1;
    }

    private int[] solve1(int n, int[][] queries) {
        int q = queries.length;
        int[] res = new int[q];
        int tot = 0;
        for (int[] query : queries) {
            int temp = 2;
            int u = query[0], v = query[1];
            while (u != v) {
                if (u < v) {
                    v /= 2;
                } else {
                    u /= 2;
                }
                temp += 1;
            }
            res[tot++] = temp;
        }
        return res;
    }
}
