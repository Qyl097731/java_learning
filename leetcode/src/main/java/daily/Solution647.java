package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/18 17:48
 * @author: qyl
 */
public class Solution647 {
    final int MAXN = (int) 2e6 + 10;
    char[] ma = new char[MAXN * 2];
    int[] mp = new int[MAXN * 2];

    public int countSubstrings(String s) {
//        return solve1 (s);
        return solve2 (s);
    }

    private int solve2(String s) {
        return manacher (s.toCharArray (), s.length ());
    }


    int manacher(char[] s, int len) {
        int l = 0;
        ma[l++] = '$';
        ma[l++] = '#';
        for (int i = 0; i < len; i++) {
            ma[l++] = s[i];
            ma[l++] = '#';
        }
        ma[l++] = '!';
        int r = 0, id = 0, ans = 0;
        for (int i = 0; i < l; i++) {
            mp[i] = r > i ? Math.min (mp[2 * id - i], r - i) : 1;
            while (i - mp[i] >= 0 && ma[i + mp[i]] == ma[i - mp[i]]) mp[i]++;
            if (i + mp[i] > r) {
                r = i + mp[i];
                id = i;
            }
            ans += mp[i] / 2;
        }
        return ans;
    }

    @Test
    public void test() {
        countSubstrings ("abc");
    }

    private int solve1(String s) {
        int res = 0;
        int n = s.length ();
        for (int i = 0; i < n; i++) {
            int len = 0;
            while (len + i < n && i - len >= 0 && s.charAt (i + len) == s.charAt (i - len)) {
                len++;
            }
            res += len;
            len = 0;
            while (len + i + 1 < n && i - len >= 0 && s.charAt (i + len + 1) == s.charAt (i - len)) {
                len++;
            }
            res += len;
        }
        return res;
    }
}
