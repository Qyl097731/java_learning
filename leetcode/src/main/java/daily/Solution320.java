package daily;

import org.junit.jupiter.api.Test;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date 2023/2/17 10:26
 * @author: qyl
 */
public class Solution320 {
    int[] res;
    int n, m, k;

    public int[] maxNumber(int[] nums1, int[] nums2, int k) {
        n = nums1.length;
        m = nums2.length;
        res = new int[k];
        this.k = k;
        int start = Math.max (0, k - m), end = Math.min (k, n);
        for (int i = start; i <= end; i++) {
            int[] sub1 = maxSub (nums1, i);
            int[] sub2 = maxSub (nums2, k - i);
            int[] curMax = merge (sub1, sub2);
            if (compare (res, 0, curMax, 0) < 0) {
                System.arraycopy (curMax, 0, res, 0, k);
            }
        }
        return res;
    }

    private int compare(int[] res, int i, int[] curMax, int j) {
        for (; i < res.length && j < curMax.length; i++, j++) {
            if (res[i] != curMax[j]) {
                return res[i] - curMax[j];
            }
        }
        return (res.length - i) - (curMax.length - j);
    }

    private int[] maxSub(int[] nums1, int len) {
        Deque<Integer> q = new ArrayDeque<> ();
        int i = 0, size = 0;
        int left = nums1.length;
        while (i < nums1.length) {
            while (!q.isEmpty () && q.peekLast () < nums1[i] && left + size - 1 >= len) {
                q.pollLast ();
                size--;
            }
            q.offerLast (nums1[i++]);
            size++;
            left--;
        }
        int[] ans = new int[len];
        int j = 0;
        while (j < len) {
            ans[j++] = q.pollFirst ();
        }
        return ans;
    }

    private int[] merge(int[] sub1, int[] sub2) {
        int[] res = new int[k];
        int i = 0, j = 0, id = 0;
        while (id < k) {
            int num;
            if (compare (sub1, i, sub2, j) > 0) {
                num = sub1[i++];
            } else {
                num = sub2[j++];
            }
            res[id++] = num;
        }
        return res;
    }

    @Test
    public void test() {
        maxNumber (new int[]{6, 7}, new int[]{6, 0, 4}, 5);
    }
}
