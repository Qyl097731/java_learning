package daily;

import org.junit.jupiter.api.Test;

import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @description
 * @date:2023/1/8 10:32
 * @author: qyl
 */
public class Solution6283 {
    public long maxKelements(int[] nums, int k) {
        PriorityQueue<Integer> set = new PriorityQueue<> (new Comparator<Integer> () {
            @Override
            public int compare(Integer o1, Integer o2) {
                return -o1.compareTo (o2);
            }
        });
        for (int num : nums) {
            set.offer (num);
        }
        long res = 0;
        for (int i = 0; i < k; i++) {
            int num = set.poll ();
            res += num;
            set.offer ((int) Math.ceil (num / 3.0));
        }
        return res;
    }

    @Test
    public void test() {
        maxKelements (new int[]{1, 10, 3, 3, 3}, 3);
    }

}
