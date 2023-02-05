package daily;

/**
 * @description
 * @date 2023/2/5 23:02
 * @author: qyl
 */
public class Solution6347 {
    public int[] vowelStrings(String[] words, int[][] queries) {
        int n = words.length;
        int[] pre = new int[n + 1];
        for (int i = 0; i < n; i++) {
            pre[i + 1] += pre[i] + count (words[i]);
        }
        int q = queries.length;
        int[] res = new int[q];
        for (int i = 0; i < q; i++) {
            int[] query = queries[i];
            int l = query[0], r = query[1];
            res[i] = pre[r + 1] - pre[l];
        }
        return res;
    }

    private int count(String word) {
        return (check (word, 0) && check (word, word.length () - 1)) ? 1 : 0;
    }

    private boolean check(String word, int i) {
        return word.charAt (i) == 'a' ||
                word.charAt (i) == 'e' ||
                word.charAt (i) == 'i' ||
                word.charAt (i) == 'o' ||
                word.charAt (i) == 'u';
    }

}
