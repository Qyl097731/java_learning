package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/31 23:50
 * @author: qyl
 */
public class Solution2037 {
    public int minMovesToSeat(int[] seats, int[] students) {
        Arrays.sort (seats);
        Arrays.sort (students);
        int res = 0;
        for (int i = 0; i < seats.length; i++) {
            res += Math.abs (seats[i] - students[i]);
        }
        return res;
    }
}
