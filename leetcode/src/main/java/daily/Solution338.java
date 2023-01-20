package daily;

/**
 * @description
 * @date:2023/1/20 20:08
 * @author: qyl
 */
public class Solution338 {
    public int[] countBits(int n) {
//        return solve1(n);
//        return solve2(n);
//        return solve3(n);
        return solve4 (n);
    }

    private int[] solve4(int n) {
        int[] bits = new int[n + 1];
        for (int i = 1; i <= n; i++) {
            bits[i] = bits[i & (i - 1)] + 1;
        }
        return bits;
    }

    int lowbit(int x) {
        return x & (-x);
    }

    private int[] solve3(int n) {
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = bits[i - lowbit (i)] + 1;
        }
        return bits;
    }

    private int[] solve2(int n) {
        int[] bits = new int[n + 1];
        for (int i = 0; i <= n; i++) {
            bits[i] = bits[i / 2] + (i & 1);
        }
        return bits;
    }

    private int[] solve1(int n) {
        return countBits (n);
    }

    private int countOnes(int x) {
        int res = 0;
        while (x > 0) {
            x &= (x - 1);
            res++;
        }
        return res;
    }
}
