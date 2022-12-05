package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/5 10:26
 * @author: qyl
 */
public class Solution1774 {
    public int closestCost(int[] baseCosts, int[] toppingCosts, int target) {
        int x = Arrays.stream (baseCosts).min ( ).getAsInt ( );
        if (x >= target) return x;

        boolean[] can = new boolean[target * 2 + 1];
        int res = Integer.MAX_VALUE, dif = Integer.MAX_VALUE;
        for (int baseCost : baseCosts) {
            if (baseCost <= 2 * target) {
                can[baseCost] = true;
            }
        }

        for (int t : toppingCosts) {
            for (int count = 0; count < 2; count++) {
                for (int i = target * 2; i >= t; i--) {
                    can[i] |= can[i - t];
                }
            }
        }

        for (int i = 0; i <= 2 * target; i++) {
            if (can[i]) {
                int tmp = Math.abs (target - i);
                if (tmp < dif) {
                    res = i;
                    dif = tmp;
                }
            }
        }
        return res;
    }

    @Test
    public void test() {
        int[] base = {1, 7};
        int[] tops = {3, 4};
        System.out.println (closestCost (base, tops, 10));
    }
}
