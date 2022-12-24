package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/23 22:28
 * @author: qyl
 */
public class Solution1753 {
    public int maximumScore(int a, int b, int c) {
        int[] stones = {a, b, c};
        Arrays.sort (stones);
        if (stones[0] + stones[1] <= stones[2]) {
            return stones[0] + stones[1];
        } else {
            return (stones[0] + stones[1] - stones[2]) / 2 + stones[2];
        }
    }
}
