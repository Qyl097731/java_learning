package day06;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author qyl
 * @program Pro438.java
 * @Description 找到字符串中所有字母异位词
 * @createTime 2022-08-30 11:38
 */
public class Pro438 {
    public static void main(String[] args) {
        System.out.println(new Solution438().findAnagrams(
                "cbaebabacd"
                , "abc"));
    }
}

class Solution438 {
    public List<Integer> findAnagrams(String s, String p) {
        List<Integer> res = new ArrayList<>();
        int[] snum = new int[26];
        int[] pnum = new int[26];
        int slen = s.length(), plen = p.length();

        if (slen < plen) {
            return res;
        }

        for (int i = 0; i < plen; i++) {
            snum[s.charAt(i) - 'a']++;
            pnum[p.charAt(i) - 'a']++;
        }

        if (Arrays.equals(snum, pnum)) {
            res.add(0);
        }

        for (int i = 0; i + plen < slen; i++) {
            snum[s.charAt(i) - 'a']--;
            snum[s.charAt(i + plen) - 'a']++;
            if (Arrays.equals(snum, pnum)) {
                res.add(i+1);
            }
        }

        return res;
    }
}