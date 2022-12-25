package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/25 11:57
 * @author: qyl
 */
public class Solutions6271 {
    public int maximumTastiness(int[] price, int k) {
        Arrays.sort (price);
        int l = 0, r = Arrays.stream (price).max ( ).getAsInt ( ), mid;
        int res = r;
        while (l <= r) {
            mid = (l + r) / 2;
            if (check (price, k, mid)) {
                res = mid;
                l = mid + 1;
            } else {
                r = mid - 1;
            }
        }
        return res;
    }

    private boolean check(int[] price, int k, int mid) {
        int i = 0;
        k--;
        while (i < price.length) {
            int idx = find (price, price[i] + mid, i);
            if (idx == -1 && k > 0) {
                return false;
            }
            i = idx;
            k--;
            if (k == 0) break;
        }
        return k <= 0;
    }

    private int find(int[] price, int num, int l) {
        int r = price.length - 1, mid, res = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            if (price[mid] >= num) {
                res = mid;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }

    @Test
    public void test() {
        maximumTastiness (new int[]{13, 5, 1, 8, 21, 2}, 3);
    }
}
