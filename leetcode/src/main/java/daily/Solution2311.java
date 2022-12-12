package daily;

/**
 * @description
 * @date:2022/12/12 11:16
 * @author: qyl
 */
public class Solution2311 {
    public int longestSubsequence(String s, int k) {
        int n = s.length ( );
        int res = 0;
        long temp = 0;
        for (int i = n - 1; i >= 0; i--) {
            if (temp > k) {
                if (s.charAt (i) == '0') {
                    res++;
                }
                continue;
            }
            if (s.charAt (i) == '1') {
                temp = temp + (1L << (n - i - 1));
            }
            if (temp > k) {
                continue;
            }
            res++;
        }
        return res;
    }
}
