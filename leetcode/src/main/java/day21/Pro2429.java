package day21;

import java.util.ArrayDeque;

/**
 * @description
 * @date:2022/10/12 20:35
 * @author: qyl
 */
class Solution2429 {
    public int minimizeXor(int num1, int num2) {
        int z = 0,z2 = 0;
        for (int i = 0; (1<<i) <= num2 ; i++) {
            z += (num2 & 1<<i) > 0 ? 1 : 0;
        }
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 0; (1<<i) <= num1 ; i++) {
            dq.addFirst((1<<i & num1) > 0 ? 1 : 0);
        }
        int size = Math.max(dq.size(),z);
        int[] kk = new int[size] ;
        int[] res = new int[size];
        int tot = 0;
        while (!dq.isEmpty()) {
            kk[tot++] = dq.pollFirst();
        }
        for (int i = 0; i < tot && z > 0; i++) {
            if (kk[i] == 1){
                res[i] = 1;
                z--;
            }
        }
        if (z > 0) {
            for (int i = size - 1; z > 0&& i >= 0; i--) {
                if (res[i] != 1) {
                    res[i] = 1;
                    z--;
                }
            }
        }
        int r = 0;
        for (int i = 0; i < size; i++) {
            r += (1<<i) * res[size-i-1];
        }
        return r;
    }
}
