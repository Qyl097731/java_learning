package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @date:2022/12/10 23:03
 * @author: qyl
 */
public class Solution6263 {
    Map<Integer, Boolean> used = new HashMap<> ( );

    public int maxJump(int[] stones) {
//        return solveA(stones);
        return solveB (stones);
    }

    private int solveB(int[] stones) {
        int n = stones.length;
        int res = stones[1] - stones[0];
        for (int i = 0; i + 2 < n; i++) {
            res = Math.max (stones[i + 2] - stones[i], res);
        }
        return res;
    }

    private int solveA(int[] stones) {
        int n = stones.length;
        int l = 0, r = stones[n - 1], mid, dest;

        for (int i = 0; i < n; i++) {
            used.put (stones[i], false);
        }

        int res = r;
        dest = r;
        while (l <= r) {
            mid = (l + r) / 2;
            if (check (mid, stones, dest)) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }

    private boolean check(int dis, int[] stones, int dest) {
        int start = 0, pre = 0, end = stones.length - 1;
        List<Integer> use = new ArrayList<> ( );
        boolean flag = true;
        while (start < dest) {
            int floor = findMax (stones, start + dis, pre, end);
            if (floor == pre) {
                flag = false;
                break;
            } else {
                start = stones[floor];
                use.add (start);
                pre = floor;
                used.put (start, true);
            }
        }

        used.put (dest, false);
        pre = stones[0];
        for (int i = 1; i <= end; i++) {
            if (!used.get (stones[i])) {
                if (stones[i] - pre > dis) {
                    flag = false;
                    break;
                } else {
                    pre = stones[i];
                }
            }
        }

        for (int i = 0; i < use.size ( ); i++) {
            used.put (use.get (i), false);
        }
        return flag;
    }

    int findMax(int[] nums, int num, int start, int end) {
        int res = 0;
        int l = start, r = end, mid;
        while (l <= r) {
            mid = (l + r) / 2;
            if (nums[mid] <= num) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    @Test
    public void test() {
        maxJump (new int[]{0, 3, 9});
    }

}
