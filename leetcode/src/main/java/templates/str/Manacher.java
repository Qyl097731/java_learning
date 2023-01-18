package templates.str;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/18 18:01
 * @author: qyl
 */
public class Manacher {
    final int MAXN = (int) 2e6 + 10;
    char[] ma = new char[MAXN * 2];
    int[] mp = new int[MAXN * 2];

    void manacher(char[] s, int len) {
        int l = 0;
        ma[l++] = '$';
        ma[l++] = '#';
        for (int i = 0; i < len; i++) {
            ma[l++] = s[i];
            ma[l++] = '#';
        }
        int r = 0, id = 0;
        for (int i = 0; i < l; i++) {
            mp[i] = r > i ? Math.min (mp[2 * id - i], r - i) : 1;
            while (i - mp[i] >= 0 && ma[i + mp[i]] == ma[i - mp[i]]) mp[i]++;
            if (i + mp[i] > r) {
                r = i + mp[i];
                id = i;
            }
        }
    }

    @Test
    public void test() {
        String s = "aaa";
        int ans = 0, n = s.length ();
        manacher (s.toCharArray (), n);
        for (int i = 0; i < 2 * n + 2; i++) {
            ans = Math.max (ans, mp[i] - 1);
        }
    }
}
