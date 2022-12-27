package daily;

/**
 * @description
 * @date:2022/12/27 19:19
 * @author: qyl
 */
public class Solution2027 {
    public int minimumMoves(String s) {
        int n = s.length ();
        int cnt = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt (i) == 'X') {
                cnt++;
                i += 2;
            }
        }
        return cnt;
    }
}
