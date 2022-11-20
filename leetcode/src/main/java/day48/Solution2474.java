package day48;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

/**
 * @description
 * @date:2022/11/19 23:30
 * @author: qyl
 */
public class Solution2474 {
    public int[] productQueries(int n, int[][] queries) {
        int cnt = 0, len = queries.length;
        int[] powers = new int[32];
        int[] res = new int[len];
        for (int i = 0; i < 32; i++) {
            int num = (1 << i);
            if ((n & num) == num) {
                powers[cnt++] = num;
            }
        }

        int mod = (int) 1e9 + 7;
        long temp = 1;
        for (int q = 0; q < len; q++) {
            int[] query = queries[q];
            temp = 1;
            for (int i = query[0]; i <= query[1]; i++) {
                temp = (temp * powers[i]) % mod;
            }
            res[q] = (int) temp;
        }
        return res;
    }

    @Test
    public void test() {
        int[][] q = {{0, 1}, {2, 2}, {0, 3}};
        System.out.println (Arrays.toString (productQueries (15, q)));
    }
}
