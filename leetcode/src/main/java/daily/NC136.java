package daily;

import org.junit.jupiter.api.Test;

import java.lang.reflect.Array;
import java.util.Arrays;

/**
 * @description
 * @date 2023/3/21 0:46
 * @author: qyl
 */
public class NC136 {
    int[] res;
    int lenth;

    public int[] solve(int[] xianxu, int[] zhongxu) {
        int n = xianxu.length;
        res = new int[n];
        build (xianxu, zhongxu, 0, n - 1, 0, n - 1, 0);
        return Arrays.copyOf (res, lenth);
    }

    // 左根右 最后一个节点作为res[dept]
    void build(int[] xianxu, int[] zhongxu, int xLeft, int xRight, int zLeft,
               int zRight, int dept) {
        lenth = Math.max (lenth, dept);
        if (xLeft > xRight) {
            return;
        }
        res[dept] = xianxu[xLeft];
        int num = xianxu[xLeft];
        for (int i = zLeft; i <= zRight; i++) {
            if (num == zhongxu[i]) {
                build (xianxu, zhongxu, xLeft + 1, xLeft + i - zLeft, zLeft, i - 1, dept + 1);
                build (xianxu, zhongxu, xLeft + i - zLeft + 1, xRight, i + 1, zRight, dept + 1);
                return;
            }
        }
    }


    @Test
    public void test() {
        solve (new int[]{1, 2, 4, 5, 6, 3}, new int[]{5, 4, 6, 2, 1, 3});
    }
}
