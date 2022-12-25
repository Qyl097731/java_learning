package daily;

/**
 * @description
 * @date:2022/12/24 22:25
 * @author: qyl
 */
public class Solution6273 {
    public int captureForts(int[] forts) {
        int res = 0;
        int n = forts.length;
        for (int i = 0; i < n; i++) {
            if (forts[i] == 1) {
                int temp = 0;
                for (int j = i - 1; j >= 0; j--) {
                    if (forts[j] == 0) {
                        temp++;
                    } else if (forts[j] == -1) {
                        res = Math.max (res, temp);
                        break;
                    } else {
                        break;
                    }
                }

                temp = 0;
                for (int j = i + 1; j < n; j++) {
                    if (forts[j] == 0) {
                        temp++;
                    } else if (forts[j] == -1) {
                        res = Math.max (res, temp);
                        break;
                    } else {
                        break;
                    }
                }
            }
        }
        return res;
    }
}
