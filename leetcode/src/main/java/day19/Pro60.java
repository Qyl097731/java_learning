package day19;

import com.sun.javafx.scene.control.skin.VirtualFlow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Timeout;


/**
 * @description
 * @date:2022/10/10 16:32
 * @author: qyl
 */
class Solution60 {
    int[] fac;

    public String getPermutation(int n, int k) {
        k--;
        VirtualFlow.ArrayLinkedList<Integer> list = new VirtualFlow.ArrayLinkedList<>();
        fac = new int[n + 1];
        fac[0] = 1;
        for (int i = 1; i <= n; i++) {
            fac[i] = fac[i - 1] * i;
            list.addLast(i);
        }

        StringBuilder res = new StringBuilder();
        for (int i = n; i >= 1; i--) {
            int r = k % fac[i - 1];
            int t = k / fac[i - 1];
            k = r;
            res.append(list.get(t));
            list.remove(t);
        }
        return res.toString();
    }

    @Test
    @Timeout(value = 1)
    public void test1() {

        Assertions.assertEquals("213", getPermutation(3, 3));
    }
}
