package day49;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @description
 * @date:2022/11/21 16:24
 * @author: qyl
 */
public class Solution2260 {
    public int minimumCardPickup(int[] cards) {
//        return solveA(cards);
        return solveB (cards);
    }

    private int solveB(int[] cards) {
        Map<Integer, Integer> map = new HashMap<> ( );
        int n = cards.length;
        int res = n + 1;
        for (int i = 0; i < n; i++) {
            Integer pre = map.get (cards[i]);
            if (pre != null) {
                res = Math.min (res, i - pre + 1);
            }
            map.put (cards[i], i);
        }
        return res == n + 1 ? -1 : res;
    }

    private int solveA(int[] cards) {
        int l = 0, r = l, n = cards.length;
        int res = n + 1;

        int[] cnt = new int[1000006];
        Arrays.fill (cnt, 0);
        while (r < n) {
            cnt[cards[r]]++;
            if (cnt[cards[r]] > 1) {
                while (l < r && cnt[cards[r]] > 1) {
                    res = Math.min (res, r - l + 1);
                    cnt[cards[l++]]--;
                }
            }
            r++;
        }
        return res == n + 1 ? -1 : res;
    }

    @Test
    public void test() {
        int[] cards = {1, 0, 5, 3};
        Assertions.assertEquals (minimumCardPickup (cards), -1);
        cards = new int[]{3, 4, 2, 3, 4, 7};
        Assertions.assertEquals (minimumCardPickup (cards), 4);

    }
}
