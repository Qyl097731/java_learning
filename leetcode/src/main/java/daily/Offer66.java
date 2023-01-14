package daily;

/**
 * @description
 * @date:2023/1/14 19:48
 * @author: qyl
 */
public class Offer66 {
    public int[] constructArr(int[] a) {
        if (a == null || a.length == 0) {
            return new int[0];
        }
        int n = a.length;
        int[] res = new int[n];
        int[] left = new int[n], right = new int[n];
        left[0] = right[n - 1] = 1;
        for (int i = 1; i < n; i++) {
            left[i] = left[i - 1] * a[i - 1];
        }
        for (int i = n - 2; i >= 0; i--) {
            right[i] = right[i + 1] * a[i + 1];
        }
        for (int i = 0; i < n; i++) {
            res[i] = left[i] * right[i];
        }
        return res;
    }
}
