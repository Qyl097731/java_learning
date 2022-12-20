package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/20 17:12
 * @author: qyl
 */
public class Solution2332 {
    public int latestTimeCatchTheBus(int[] buses, int[] passengers, int capacity) {
        Arrays.sort (buses);
        Arrays.sort (passengers);
        int r = 0, c = 0;
        for (int bus : buses) {
            for (c = capacity; c > 0 && r < passengers.length && passengers[r] <= bus; c--) {
                r++;
            }
        }
        r--;
        int ans = c > 0 ? buses[buses.length - 1] : passengers[r];
        while (r >= 0 && ans == passengers[r--]) {
            --ans;
        }
        return ans;
    }
}
