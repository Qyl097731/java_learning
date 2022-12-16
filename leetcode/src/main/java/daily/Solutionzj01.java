package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/16 16:56
 * @author: qyl
 */
public class Solutionzj01 {
    public boolean canReceiveAllSignals(int[][] intervals) {
        Arrays.sort (intervals, (o1, o2) -> {
            if (o1[0] == o2[0]) {
                return o1[1] - o2[1];
            }
            return o1[0] - o2[0];
        });
        int n = intervals.length;
        int preM = 0;
        for (int i = 0; i < n; i++) {
            if (intervals[i][0] < preM) {
                return false;
            }
            preM = intervals[i][1];
        }
        return true;
    }
}
