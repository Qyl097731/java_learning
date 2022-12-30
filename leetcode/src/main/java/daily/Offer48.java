package daily;

import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/12/30 19:51
 * @author: qyl
 */
public class Offer48 {
    public int lengthOfLongestSubstring(String s) {
        int res = 0;
        int n = s.length ();
        Set<Character> nums = new HashSet<> ();
        int l = 0, r = l;
        while (r < n) {
            char c = s.charAt (r);
            if (!nums.contains (c)) {
                nums.add (c);
                res = Math.max (res, r - l + 1);
            } else {
                while (l <= r && nums.contains (c)) {
                    nums.remove (s.charAt (l));
                    l++;
                }
                nums.add (c);
            }
            r++;
        }
        return res;
    }

    @Test
    public void test() {
        System.out.println (lengthOfLongestSubstring ("pwwkew"));
    }
}
