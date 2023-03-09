package daily;

/**
 * @description
 * @date 2023/3/9 23:17
 * @author: qyl
 */
public class NC91 {
    public int[] LIS(int[] arr) {
        int n = arr.length;
        int[] res = new int[n];
        int[] len = new int[n];
        int left, right, mid, total = 0, index;
        for (int i = 0; i < n; i++) {
            int num = arr[i];
            if (total == 0 || res[total] < num) {
                res[total++] = num;
                len[i] = total;
            } else {
                left = 0;
                right = total;
                index = 0;
                while (left <= right) {
                    mid = (left + right) / 2;
                    if (res[mid] > num) {
                        index = mid;
                        right--;
                    } else {
                        left++;
                    }
                }
                len[i] = index + 1;
                res[index] = num;
            }
        }
        int[] nums = new int[total];
        for (int i = n - 1; i >= 0; i--) {
            if (len[i] == total) {
                nums[--total] = arr[i];
            }
        }
        return nums;
    }
}
