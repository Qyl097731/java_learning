package daily;

/**
 * @description
 * @date:2022/12/7 21:38
 * @author: qyl
 */
public class Solution2283 {
    int[] cnt = new int[10];

    public boolean digitCount(String num) {
        int n = num.length ( );
        for (int i = 0; i < n; i++) {
            cnt[num.charAt (i) - '0']++;
        }
        for (int i = 0; i < n; i++) {
            if (cnt[i] != num.charAt (i) - '0') {
                return false;
            }
        }
        return true;
    }
}
