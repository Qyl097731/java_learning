package templates.sort;

import java.util.Arrays;

/**
 * @description
 * @date 2023/3/9 20:37
 * @author: qyl
 */
public class BubbleSort {
    public int findKth(int[] a, int n, int K) {
        for (int i = 1; i <= K; i++) {
            for (int j = n - i - 1; j >= i - 1; j--) {
                if (a[j] <= a[j + 1]) {
                    a[j] ^= a[j + 1];
                    a[j + 1] ^= a[j];
                    a[j] ^= a[j + 1];
                }
            }
            System.out.println (Arrays.toString (a));
        }
        return a[K - 1];
    }
}
