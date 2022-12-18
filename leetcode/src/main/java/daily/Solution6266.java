package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/18 17:30
 * @author: qyl
 */
public class Solution6266 {
    static int N = 100005;
    static boolean[] prime = new boolean[N];

    static {
        prime[0] = prime[1] = false;
        for (int i = 2; i < N; i++) {
            boolean flag = true;
            for (int j = 2; j * j <= i; j++) {
                if (i % j == 0) {
                    flag = false;
                    break;
                }
            }
            prime[i] = flag;
        }
    }

    public int smallestValue(int n) {
        if (prime[n]) {
            return n;
        }
        int pre = n;
        while (n != 0) {
            int temp = 0;
            for (int i = 2; i <= n; i++) {
                if (prime[i]) {
                    while (n % i == 0) {
                        temp += i;
                        n /= i;
                    }
                }
            }
            n = temp;
            if (prime[n] || pre == n) {
                break;
            }
            pre = n;
        }
        return n;
    }

    @Test
    public void test() {
        Assertions.assertEquals (smallestValue (15), 5);
    }
}
