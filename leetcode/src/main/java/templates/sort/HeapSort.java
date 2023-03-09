package templates.sort;

/**
 * @description
 * @date 2023/3/9 21:37
 * @author: qyl
 */
public class HeapSort {
    public int findKth(int[] a, int n, int K) {
        heapSort (a, n);
        return a[n - K];
    }

    private void heapSort(int[] a, int n) {
        for (int i = (n - 1 - 1) / 2; i >= 0; i--) {
            AdjustDown (a, i, n);
        }
        for (int i = n - 1; i > 0; i--) {
            swap (a, i, 0);
            AdjustDown (a, 0, i);
        }
    }

    private void AdjustDown(int[] a, int parent, int n) {
        int child = 2 * parent + 1;
        while (child < n) {
            child = (child + 1 < n && a[child] < a[child + 1]) ? child + 1 : child;
            if (a[child] >= a[parent]) {
                swap (a, child, parent);
                parent = child;
                child = 2 * parent + 1;
            } else {
                break;
            }
        }
    }

    private void swap(int[] a, int l, int r) {
        a[l] ^= a[r];
        a[r] ^= a[l];
        a[l] ^= a[r];
    }
}
