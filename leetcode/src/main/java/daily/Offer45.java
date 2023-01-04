package daily;

import java.util.Arrays;

/**
 * @description
 * @date:2023/1/4 18:01
 * @author: qyl
 */
public class Offer45 {
    public String minNumber(int[] nums) {
        int n = nums.length;
        String[] strs = new String[n];
        StringBuilder res = new StringBuilder ();
        for (int i = 0; i < n; i++) {
            strs[i] = String.valueOf (nums[i]);
        }
        Arrays.sort (strs, (x, y) -> (x + y).compareTo (y + x));
        for (int i = 0; i < n; i++) {
            res.append (strs[i]);
        }
        return res.toString ();
    }
}
