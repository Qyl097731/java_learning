package day37;

/**
 * @description
 * @date:2022/10/31 14:16
 * @author: qyl
 */
public class Solution481 {
    public int magicalString(int n) {
        int res = 1, cur = 2;
        if (n < 4) return res;
        char[] s = new char[n + 1];
        s[0] = '1';
        s[1] = '2';
        s[2] = '2';
        for (int i = 2, j = 3; j < n; i++) {
            cur = 3 - cur;
            int cnt = s[i] - '0';
            while (cnt > 0) {
                res += cur == 1 ? 1 : 0;
                s[j++] = (char) (48 + cur);
                if (j == n) return res;
                cnt--;
            }
        }
        return res;
    }
}
