package daily;

/**
 * @description
 * @date:2022/12/18 17:30
 * @author: qyl
 */
public class Solution6265 {
    public int similarPairs(String[] words) {
        int n = words.length;
        int res = 0;
        for (int i = 0; i < n; i++) {
            int[] c = new int[26];
            for (int j = 0; j < words[i].length ( ); j++) {
                c[words[i].charAt (j) - 'a'] = 1;
            }
            for (int j = i + 1; j < n; j++) {
                int[] cc = new int[26];
                int add = 1;
                for (int k = 0; k < words[j].length ( ); k++) {
                    cc[words[j].charAt (k) - 'a'] = 1;
                }
                for (int k = 0; k < 26; k++) {
                    if (cc[k] != c[k]) {
                        add = 0;
                        break;
                    }
                }
                res += add;
            }
        }
        return res;
    }
}
