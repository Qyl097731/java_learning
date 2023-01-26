package daily;

/**
 * @description
 * @date:2023/1/26 18:44
 * @author: qyl
 */
public class Solution1663 {
    public String getSmallestString(int n, int k) {
        StringBuilder res = new StringBuilder ();
        for (int i = 1; i <= n; i++) {
            int lower = Math.max (1, k - (n - i) * 26);
            k -= lower;
            res.append ((char) ('a' + lower - 1));
        }
        return res.toString ();
    }
}
