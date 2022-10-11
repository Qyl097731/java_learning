package day20;

import java.util.Arrays;

/**
 * @description
 * @date:2022/10/11 18:32
 * @author: qyl
 */
public class Pro2433 {
    public static void main(String[] args) {
        System.out.println(Arrays.toString(new Solution2433().findArray(new int[]{5, 2, 0, 3, 1})));
    }
}

class Solution2433 {
    public int[] findArray(int[] pref) {
        int len = pref.length;
        int[] res = new int[len];
        res[0] = pref[0];
        for (int i = 1; i < len; i++) {
            res[i] = pref[i] ^ pref[i-1];
        }
        return res;
    }
}
