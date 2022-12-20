package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/19 17:25
 * @author: qyl
 */
public class Offer11 {
    public int minArray(int[] numbers) {
//        return solve1(numbers);
        return solve2 (numbers);
    }

    private int solve2(int[] numbers) {
        int l = 0, r = numbers.length - 1, mid;
        int res = 0;
        while (l <= r) {
            mid = l + r >> 1;
            if (numbers[res] > numbers[mid]) {
                res = mid;
            }
            if (numbers[mid] < numbers[r]) {
                r = mid - 1;
            } else if (numbers[mid] == numbers[r]) {
                r--;
            } else {
                l = mid + 1;
            }
        }
        return numbers[res];
    }

    @Test
    public void test() {
        minArray (new int[]{3, 1, 1});
    }

    private int solve1(int[] numbers) {
        int res = Integer.MAX_VALUE;
        for (int number : numbers) {
            res = Math.min (res, number);
        }
        return res;
    }
}
