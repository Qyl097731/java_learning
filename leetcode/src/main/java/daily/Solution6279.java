package daily;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2023/1/1 16:59
 * @author: qyl
 */
public class Solution6279 {
    static boolean[] isPrime = new boolean[1005];

    static {
        isPrime[0] = isPrime[1] = false;
        for (int i = 2; i < isPrime.length; i++) {
            isPrime[i] = true;
            for (int j = 2; j < i / j; j++) {
                if (i % j == 0) {
                    isPrime[i] = false;
                }
            }
        }
    }

    public int distinctPrimeFactors(int[] nums) {
        Set<Integer> set = new HashSet<> ();
        for (int num : nums) {
            for (int i = 2; i <= num; i++) {
                if (isPrime[i]) {
                    while (num % i == 0 && num > 0) {
                        num /= i;
                        set.add (i);
                    }
                }
            }
        }
        return set.size ();
    }

    @Test
    public void test() {
        distinctPrimeFactors (new int[]{2, 4, 3, 7, 10, 6});
    }
}
