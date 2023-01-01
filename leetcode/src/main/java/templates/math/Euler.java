package templates.math;

import java.util.Arrays;

/**
 * @description 欧拉筛
 * @date:2023/1/1 17:47
 * @author: qyl
 */
public class Euler {
    static int MAXN = 1000005;
    static boolean[] isprime = new boolean[MAXN];
    static int[] prime = new int[MAXN];
    static int cnt = 0;

    static {
        Arrays.fill (isprime, true);
        isprime[1] = false;
        for (int i = 2; i < MAXN; ++i) {
            if (isprime[i]) {
                prime[++cnt] = i;
            }

            for (int j = 1; j <= cnt && i * prime[j] < MAXN; ++j) {
                isprime[i * prime[j]] = false;
                if (i % prime[j] == 0) {
                    break;
                }
            }
        }
    }
}
