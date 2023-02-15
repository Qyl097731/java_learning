package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date 2023/2/14 17:40
 * @author: qyl
 */
public class Solution1124 {
    public int longestWPI(int[] hours) {

//        return solve1 (hours);
//        return solve2 (hours);
        return solve3 (hours);
    }

    private int solve3(int[] hours) {
        int n = hours.length;
        int res = 0;
        int s = 0;
        Map<Integer, Integer> map = new HashMap<> ();
        for (int i = 0; i < n; i++) {
            s += hours[i] > 8 ? 1 : -1;
            if (s > 0) {
                res = Math.max (res, i + 1);
            } else {
                if (map.containsKey (s - 1)) {
                    res = Math.max (res, i - map.get (s - 1));
                }
            }
            if (!map.containsKey (s)) {
                map.put (s, i);
            }
        }
        return res;
    }

    private int solve2(int[] hours) {
        int n = hours.length;
        int res = 0;
        int[] sum = new int[n + 1];
        Deque<Integer> stack = new ArrayDeque<> ();
        stack.push (0);
        for (int i = 0; i < n; i++) {
            sum[i + 1] = sum[i] + (hours[i] > 8 ? 1 : -1);
            if (sum[stack.peek ()] > sum[i]) {
                stack.push (i);
            }
        }
        for (int i = n; i >= 1; i--) {
            while (!stack.isEmpty () && sum[stack.peek ()] < sum[i]) {
                res = Math.max (res, i - stack.pop ());
            }
        }
        return res;
    }

    private int solve1(int[] hours) {
        int n = hours.length;
        int res = 0;
        int sum = 0;
        for (int i = 0; i < n; i++) {
            for (int j = i; j < n; j++) {
                if (hours[j] > 8) {
                    sum++;
                } else {
                    sum--;
                }
                if (sum > 0) {
                    res = Math.max (res, j - i + 1);
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        longestWPI (new int[]{9, 6, 9});
    }
}
