package daily;

/**
 * @description
 * @date 2023/2/16 23:13
 * @author: qyl
 */
public class Solution125 {
    public boolean isPalindrome(String s) {
        int n = s.length (), len = 0;
        int[] str = new int[n];
        for (int i = 0; i < n; i++) {
            char c = s.charAt (i);
            if (Character.isDigit (c)) {
                str[len++] = c;
            } else if (Character.isLetter (c)) {
                str[len++] = Character.toLowerCase (c);
            }
        }
        int l = 0, r = len - 1;
        boolean res = true;
        if (len % 2 == 0) {
            while (l < r) {
                res &= str[r] == str[l];
                r--;
                l++;
            }
        } else {
            while (l <= r) {
                res &= str[r] == str[l];
                r--;
                l++;
            }
        }
        return res;
    }
}
