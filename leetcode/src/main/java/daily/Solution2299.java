package daily;

/**
 * @description
 * @date:2022/12/10 15:48
 * @author: qyl
 */
public class Solution2299 {
    public boolean strongPasswordCheckerII(String password) {
        char[] chars = password.toCharArray ( );
        boolean flag = false, res = true;
        for (char c : chars) {
            if (Character.isDigit (c)) {
                flag = true;
            }
        }
        res &= flag;

        flag = false;
        for (char c : chars) {
            if (Character.isLowerCase (c)) {
                flag = true;
            }
        }
        res &= flag;

        flag = false;
        for (char c : chars) {
            if (Character.isUpperCase (c)) {
                flag = true;
            }
        }
        res &= flag;

        char[] special = "!@#$%^&*()-+".toCharArray ( );
        flag = false;
        for (char c : chars) {
            for (char c1 : special) {
                if (c1 == c) {
                    flag = true;
                }
            }
        }
        res &= flag;

        flag = true;
        char pre = '~';
        for (char c : chars) {
            if (pre == c) {
                flag = false;
            }
            pre = c;
        }
        res &= flag;

        return res && chars.length >= 8;
    }
}
