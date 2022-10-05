package day12;

import java.util.Arrays;

/**
 * @description
 * @date:2022/10/1 14:41
 * @author: qyl
 */
public class Pro42 {
    public static void main(String[] args) {
        System.out.println(new Solution42().multiply("999", "999"));
    }
}

class Solution42 {
    public String multiply(String num1, String num2) {
        int len1 = num1.length();
        int len2 = num2.length();
        StringBuilder sbNum1 = new StringBuilder(num1).reverse();
        StringBuilder sbNum2 = new StringBuilder(num2).reverse();
        int[] res = new int[len1 + len2 + 1];
        for (int i = 0; i < len1 + len2 + 1; i++) {
            res[i] = 0;
        }
        for (int i = 0; i < len1; i++) {
            int value1 = sbNum1.charAt(i) - '0';
            for (int j = 0; j < len2; j++) {
                int value2 = sbNum2.charAt(j) - '0';
                int temp = value1 * value2;
                int ori = res[i + j];
                ori += temp;
                res[i + j] = ori % 10;

                int carry = res[i + j + 1];
                carry += ori / 10;
                res[i + j + 1] = carry;
            }
        }
        if (res[len1 + len2 - 1] > 10) {
            res[len1 + len2] = res[len1 + len2 - 1] / 10;
            res[len1 + len2 - 1] = res[len1 + len2 - 1] % 10;
        }

        StringBuilder sb = new StringBuilder();
        int flag = 0;
        for (int i = len1 + len2; i >= 0; i--) {
            if (res[i] != 0) {
                flag = 1;
                while (i >= 0) {
                    sb.append((char) (res[i] + '0'));
                    i--;
                }
            }
        }
        return flag == 1?sb.toString():"0";
    }
}
