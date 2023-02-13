package daily;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date 2023/2/13 18:53
 * @author: qyl
 */
public class Solution1138 {
    StringBuilder res = new StringBuilder ();
    Map<Character, int[]> dir = new HashMap<> ();
    String[] g = {"abcde", "fghij", "klmno", "pqrst", "uvwxy", "zZZZZ"};
    boolean[][] visit;

    public String alphabetBoardPath(String target) {
        int n = g.length, m = g[0].length ();
        dir.put ('U', new int[]{-1, 0});
        dir.put ('D', new int[]{1, 0});
        dir.put ('L', new int[]{0, -1});
        dir.put ('R', new int[]{0, 1});
        visit = new boolean[n][m];
        int x = 0, y = 0;
        for (int i = 0; i < target.length (); i++) {
            Queue<PointInfo> queue = new ArrayDeque<> ();
            queue.offer (new PointInfo (x, y, 0, ""));
            for (int j = 0; j < n; j++) {
                Arrays.fill (visit[j], false);
            }
            while (!queue.isEmpty ()) {
                PointInfo pt = queue.poll ();
                x = pt.x;
                y = pt.y;
                String path = pt.nowPath;
                int size = pt.size;
                if (target.charAt (i) == g[x].charAt (y)) {
                    res.append (path).append ('!');
                    break;
                }
                for (Map.Entry<Character, int[]> entry : dir.entrySet ()) {
                    char c = entry.getKey ();
                    int[] d = entry.getValue ();
                    int xx = x + d[0];
                    int yy = y + d[1];
                    if (xx < n && xx >= 0 && yy < m && yy >= 0
                            && !visit[xx][yy] && Character.isLowerCase (g[xx].charAt (yy))) {
                        visit[xx][yy] = true;
                        queue.offer (new PointInfo (xx, yy, size + 1, path + c));
                    }
                }
            }
        }
        return res.toString ();
    }

    @Test
    public void test() {
        alphabetBoardPath ("leet");
    }

    private class PointInfo {
        int x, y;
        int size;
        String nowPath;

        public PointInfo(int x, int y, int size, String nowPath) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.nowPath = nowPath;
        }
    }
}

