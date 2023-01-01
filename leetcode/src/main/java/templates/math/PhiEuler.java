package templates.math;

/**
 * @description
 * @date:2023/1/1 17:51
 * @author: qyl
 */
public class PhiEuler {
    static int[] phi = new int[1000005], prim = new int[1000005];
    static int N = 1000004;
    static int tot = 0;
    static boolean[] vis = new boolean[1000005];

    static {
        phi[1] = 1;
        tot = 0;
        for (int i = 2; i <= N; i++) {
            if (!vis[i]) {
                prim[tot++] = i;
                phi[i] = i - 1;
            }
            for (int j = 0; j < tot && prim[j] <= N / i; j++) {
                vis[i * prim[j]] = true;
                if (i % prim[j] == 0) {
                    phi[i * prim[j]] = phi[i] * prim[j];
                    break;
                } else {
                    phi[i * prim[j]] = phi[i] * (prim[j] - 1);
                }
            }
        }
    }
}
