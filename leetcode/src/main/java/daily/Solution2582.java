package daily;

/**
 * @description
 * @date 2023/3/12 0:42
 * @author: qyl
 */
public class Solution2582 {
    public int passThePillow(int n, int time) {
        int round = time / (n - 1);
        time %= (n - 1);
        if (round % 2 == 0) {
            return time + 1;
        } else {
            return n - time;
        }
    }
}
