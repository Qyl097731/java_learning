package daily;

import java.util.ArrayDeque;
import java.util.Deque;

/**
 * @description
 * @date:2022/12/8 18:05
 * @author: qyl
 */
public class Solution2289 {
    public int totalSteps(int[] nums) {
        Deque<int[]> st = new ArrayDeque<> ( );
        int res = 0;
        for (int num : nums) {
            int cur = 0;
            while (!st.isEmpty ( ) && st.peekLast ( )[0] <= num) {
                cur = Math.max (st.pollLast ( )[1], cur);
            }
            if (st.isEmpty ( )) {
                st.offerLast (new int[]{num, 0});
            } else {
                st.offerLast (new int[]{num, cur + 1});
                res = Math.max (cur + 1, res);
            }
        }
        return res;
    }
}
