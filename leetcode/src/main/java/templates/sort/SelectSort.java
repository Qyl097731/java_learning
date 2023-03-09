package templates.sort;

/**
 * @description
 * @date 2023/3/9 22:38
 * @author: qyl
 */
public class SelectSort {
    public int findKth(int[] a, int n, int K) {
        selectSort (a, n - 1);
        return a[n - K];
    }

    private void selectSort(int[] a, int n) {
        for (int i = 0; i < n; i++) {
            int idx = i;
            for (int j = i + 1; j < n; j++) {
                idx = a[idx] <= a[j] ? j : idx;
            }
            swap (a, idx, i);
        }
    }

    private void swap(int[] a, int idx, int i) {
        a[idx] ^= a[i];
        a[i] ^= a[idx];
        a[idx] ^= a[i];
    }
}
