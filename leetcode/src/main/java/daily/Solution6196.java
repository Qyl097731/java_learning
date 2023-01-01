package daily;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2023/1/1 17:12
 * @author: qyl
 */
public class Solution6196 {
    public int minimumPartition(String s, int k) {
        int cnt = 0, tempk = k;
        while (tempk > 0) {
            cnt++;
            tempk /= 10;
        }
        int res = 0;
        int n = s.length ();
        for (int i = 0; i < n; ) {
            int flag = 0;
            for (int j = Math.min (n - i, cnt); j >= 1; j--) {
                if (Integer.parseInt (s.substring (i, i + j)) <= k) {
                    i = i + j;
                    flag = 1;
                    break;
                }
            }
            if (flag == 1) {
                res += flag;
            } else {
                return -1;
            }
        }
        return res;
    }

    @Test
    public void test() {
        minimumPartition ("165462", 60);
    }
}
