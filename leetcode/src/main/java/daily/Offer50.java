package daily;

/**
 * @description
 * @date:2022/12/23 22:21
 * @author: qyl
 */
public class Offer50 {
    public char firstUniqChar(String s) {
        int[] num = new int[26];
        char[] chars = s.toCharArray ( );
        for (char c : chars) {
            num[c - 'a']++;
        }
        for (char c : chars) {
            if (num[c - 'a'] == 1) {
                return c;
            }
        }
        return ' ';
    }
}
