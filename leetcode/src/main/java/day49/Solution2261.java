package day49;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @description
 * @date:2022/11/21 16:24
 * @author: qyl
 */
public class Solution2261 {
    public int countDistinct(int[] nums, int k, int p) {
        Set<Arr> set = new HashSet<> ( );
        int n = nums.length;
        for (int i = 0; i < n; i++) {
            int tmp = 0;
            for (int j = i; j < n; j++) {
                if (nums[j] % p == 0) {
                    tmp++;
                    if (tmp > k) {
                        break;
                    }
                }
                set.add (new Arr (Arrays.copyOfRange (nums, i, j + 1)));
            }
        }
        return set.size ( );
    }

    @Test
    public void test() {
        int[] nums = {2, 3, 3, 2, 2};
        System.out.println (countDistinct (nums, 2, 2));
    }

    class Arr {
        int[] nums;

        public Arr(int[] a) {
            nums = a;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass ( ) != o.getClass ( )) return false;
            Arr arr = (Arr) o;
            return Arrays.equals (nums, arr.nums);
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode (nums);
        }
    }
}
