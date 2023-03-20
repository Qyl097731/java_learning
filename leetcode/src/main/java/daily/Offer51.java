package daily;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.IntStream;

/**
 * @description
 * @date 2023/3/18 0:28
 * @author: qyl
 */
public class Offer51 {
    int result = 0;
    int[] tree;

    int n ;
    public int reversePairs(int[] nums) {
        n = nums.length;
        Integer[] ids = IntStream.range (0,n).boxed ().toArray (Integer[]::new);
        Arrays.sort (ids, Comparator.comparingInt (a -> nums[a]));
        System.out.println (Arrays.toString (nums));
        System.out.println (Arrays.toString (ids));
        tree = new int[n+1];
        for (int i = n - 1; i >= 0 ; i--) {
            add(ids[i]);
            result += query(ids[i]);
        }
        return result;
    }

    static int lowbit(int x) {
        return x & (-x);
    }

    int query(int x) {
        int ret = 0;
        while (x != 0) {
            ret += tree[x];
            x -= lowbit(x);
        }
        return ret;
    }

    void add(int x) {
        while (x <= n) {
            ++tree[x];
            x += lowbit(x);
        }
    }

    @Test
    public void test(){
        reversePairs (new int[]{7,5,6,4});
    }
}
