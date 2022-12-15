package daily;

/**
 * @description
 * @date:2022/12/15 22:53
 * @author: qyl
 */
public class Solution1945 {
    public int getLucky(String s, int k) {
        int n = s.length ( );
        int res = 0;
        for (int i = 0; i < n; i++) {
            int num = s.charAt (i) - '0';
            while (num > 0) {
                res += num % 10;
                num /= 10;
            }
        }
        while (--k > 0) {
            int temp = res;
            res = 0;
            while (temp > 0) {
                res += temp % 10;
                temp /= 10;
            }
        }
        return res;
    }
}
