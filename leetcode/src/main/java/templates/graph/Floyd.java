package templates.graph;

/**
 * @description
 * @date 2023/3/25 21:43
 * @author: qyl
 */
public class Floyd {
    static final int N = (int) 1e3 + 5;
    static int[][] f = new int[N][N];

    static int n, m;

    static void floyd() {
        for (int k = 1; k <= n; k++) {
            for (int i = 1; i <= n ; i++) {
                for (int j = 1; j <= n; j++) {
                    f[i][j] = Math.min (f[i][j],f[i][k] + f[k][j]);
                }
            }
        }
    }
}
