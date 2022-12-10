package daily;

/**
 * @description
 * @date:2022/12/10 18:12
 * @author: qyl
 */
public class Solution2301 {
    public boolean matchReplacement(String s, String sub, char[][] mappings) {
        int n = sub.length ( );
        int m = s.length ( );
        boolean[][] map = new boolean[128][128];
        for (char[] mapping : mappings) {
            map[mapping[0]][mapping[1]] = true;
        }
        boolean res = false;
        for (int i = 0; i + n <= m; i++) {
            boolean flag = true;
            for (int j = 0; j < n; j++) {
                char x = s.charAt (i + j), y = sub.charAt (j);
                if (!(x == y || map[y][x])) {
                    flag = false;
                }
            }
            res |= flag;
        }
        return res;
    }
}
