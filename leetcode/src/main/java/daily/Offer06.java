package daily;

import common.ListNode;

import java.util.Arrays;

/**
 * @description
 * @date:2022/12/18 16:47
 * @author: qyl
 */
public class Offer06 {
    int[] res = new int[10005];
    int tot = 0;

    public int[] reversePrint(ListNode head) {
        solve (head);
        return Arrays.copyOfRange (res, 0, tot);
    }

    private void solve(ListNode head) {
        if (head != null) {
            solve (head.next);
            res[tot++] = head.val;
        }
    }
}
