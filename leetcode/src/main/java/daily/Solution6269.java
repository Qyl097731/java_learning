package daily;

/**
 * @description
 * @date:2022/12/25 10:30
 * @author: qyl
 */
public class Solution6269 {
    public int closetTarget(String[] words, String target, int startIndex) {
        int n = words.length;
        int res = Integer.MAX_VALUE;
        for (int i = 0; i < n; i++) {
            String word = words[i];
            if (word.equals (target)) {
                int temp = 0;
                for (int j = i; j != startIndex; j = (j + 1) % n) {
                    temp++;
                }
                res = Math.min (res, temp);
                temp = 0;
                for (int j = i; j != startIndex; j = (j - 1 + n) % n) {
                    temp++;
                }
                res = Math.min (res, temp);
            }
        }
        return res == Integer.MAX_VALUE ? -1 : res;
    }
}
