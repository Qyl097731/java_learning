package daily;

/**
 * @description
 * @date:2022/12/8 17:50
 * @author: qyl
 */
public class Solution2287 {
    public int rearrangeCharacters(String s, String target) {
        int[] cnt = new int[27];
        int[] tat = new int[27];
        for (char c : s.toCharArray ( )) {
            cnt[c - 'a']++;
        }

        for (char c : target.toCharArray ( )) {
            tat[c - 'a']++;
        }

        int res = Integer.MAX_VALUE;
        for (int i = 0; i < 26; i++) {
            if (tat[i] != 0) {
                res = Math.min (cnt[i] / tat[i], res);
            }
        }
        return res;
    }
}
