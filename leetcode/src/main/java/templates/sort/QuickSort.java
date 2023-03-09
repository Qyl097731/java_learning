package templates.sort;

/**
 * @description
 * @date 2023/3/9 20:56
 * @author: qyl
 */
public class QuickSort {
    public int findKth(int[] a, int n, int K) {
        quickSort (a, 0, n - 1);
        return a[n - K];
    }

    public void quickSort(int[] a, int left, int right) {
        if (left < right) {
            int povid = partition (a, left, right);
            quickSort (a, left, povid - 1);
            quickSort (a, povid + 1, right);
        }
    }

    public int partition(int[] a, int start, int end) {
        int num = a[start];
        while (start < end) {
            while (start < end && a[end] >= num) {
                end--;
            }
            a[start] = a[end];
            while (start < end && a[start] <= num) {
                start++;
            }
            a[end] = a[start];
        }
        a[start] = num;
        return start;
    }
}
