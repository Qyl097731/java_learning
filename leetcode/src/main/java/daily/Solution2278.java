package daily;

/**
 * @description
 * @date:2022/12/6 14:08
 * @author: qyl
 */
public class Solution2278 {
    public int percentageLetter(String s, char letter) {
        int n = s.length ( );
        int num = 0;
        for (int i = 0; i < n; i++) {
            if (s.charAt (i) == letter) {
                num++;
            }
        }
        return num * 100 / n;
    }
}
