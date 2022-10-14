package day23;

import java.util.ArrayDeque;
import java.util.Arrays;

/**
 * @description
 * @date:2022/10/14 11:37
 * @author: qyl
 */
public class Pro940 {
    public static void main(String[] args) {
        System.out.println(new Solution940().distinctSubseqII("abc"));
    }
}

class Solution940 {
    public int distinctSubseqII(String s) {
        int n = s.length(), mod = (int) 1e9 + 7;
        int[] f = new int[n + 1], lst = new int[26];
        Arrays.fill(lst, -1);
        Arrays.fill(f,1);
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < 26; j++) {
                if (lst[j] != -1) {
                    f[i] = (f[i] + f[lst[j]]) % mod;
                }
            }
            lst[s.charAt(i)-'a'] = i;
        }
        int res = 0 ;
        for (int i = 0; i < 26; i++) {
            if (lst[i] != -1) {
                res += f[lst[i]];
                res %= mod;
            }
        }
        return res;
    }
}
