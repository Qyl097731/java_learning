package daily;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date 2023/2/4 23:34
 * @author: qyl
 */
public class Solution6343 {
    List<Integer> res = new ArrayList<> ();

    public int[] separateDigits(int[] nums) {
        for (int i = 0; i < nums.length; i++) {
            int num = nums[i];
            getNums (num);
        }
        return res.stream ().mapToInt (i -> i).toArray ();
    }

    private void getNums(int num) {
        List<Integer> temp = new ArrayList<> ();
        while (num > 0) {
            temp.add (0, num % 10);
            num /= 10;
        }
        res.addAll (temp);
    }
}
