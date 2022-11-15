package day45;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/15 11:23
 * @author: qyl
 */
public class Solution2457 {
    public long makeIntegerBeautiful(long n, int target) {
        long pre = n;
        long i = 10;
        while (true) {
            if (check(n, target)) {
                break;
            }
            n /= i;
            n++;
            n = n * i;
            i *= 10;

        }
        return n - pre;
    }

    private boolean check(long n, int target) {
        int sum = 0;
        while (n > 0) {
            sum += n % 10;
            n /= 10;
        }
        return sum <= target;
    }

    @Test
    public void test() {
//        System.out.println(makeIntegerBeautiful(16, 6));
        System.out.println(makeIntegerBeautiful(16, 6));
    }
}
