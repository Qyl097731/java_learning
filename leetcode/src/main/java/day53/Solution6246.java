package day53;

import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/11/27 10:29
 * @author: qyl
 */
public class Solution6246 {
    List<Integer>[] pos = new List[26];
    int[] idx = new int[26];

    public int appendCharacters(String s, String t) {
        int n = s.length ( ), m = t.length ( );
        for (int i = 0; i < 26; i++) {
            pos[i] = new ArrayList<> ( );
        }

        for (int i = 0; i < n; i++) {
            int c = s.charAt (i) - 'a';
            pos[c].add (i);
        }
        int pre = -1;
        for (int i = 0; i < m; i++) {
            int c = t.charAt (i) - 'a';
            int idx = find (c, pre);
            if (idx == -1) {
                return m - i;
            }
            pre = idx;
        }
        return 0;
    }

    private int find(int c, int pre) {
        List<Integer> list = pos[c];
        int l = 0, r = list.size ( ) - 1, mid, res = -1;
        while (l <= r) {
            mid = (l + r) / 2;
            int num = list.get (mid);
            if (num > pre) {
                res = num;
                r = mid - 1;
            } else {
                l = mid + 1;
            }
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (appendCharacters ("coaching", "coding"));
        System.out.println (appendCharacters ("abcde", "a"));
        System.out.println (appendCharacters ("z", "abcde"));
        System.out.println (appendCharacters ("ajkhe", "juh"));
    }
}
