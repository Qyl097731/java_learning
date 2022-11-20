package day48;

/**
 * @description
 * @date:2022/11/19 23:30
 * @author: qyl
 */
public class Solution2473 {
    int res = 0;
    int n;
    char[] chars;

    public int countTime(String time) {
        n = time.length ( );
        chars = new char[n];
        dfs (chars, 0, time.toCharArray ( ));
        return res;
    }

    private void dfs(char[] chars, int cur, char[] time) {
        if (cur == n) {
            if (check (new String (chars))) res++;
            return;
        }

        if (time[cur] == '?') {
            for (int i = 0; i <= 9; i++) {
                chars[cur] = (char) (i + '0');
                dfs (chars, cur + 1, time);
            }
        } else {
            chars[cur] = time[cur];
            dfs (chars, cur + 1, time);
        }
    }

    boolean check(String t) {
        String[] split = t.split (":");
        return Integer.parseInt (split[0]) <= 23 && Integer.parseInt (split[0]) >= 0
                && Integer.parseInt (split[1]) <= 59 && Integer.parseInt (split[1]) >= 0;
    }
}
