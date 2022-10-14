package day22;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/10/13 15:08
 * @author: qyl
 */
class Solution769 {
    Set<Integer> set = new HashSet<>();
    List<Integer> nums = new ArrayList<>();
    public int maxChunksToSorted(int[] arr) {
        int res = 0;
        int len = arr.length;

        for (int i = 0; i < len; i++) {
            nums.add(i);
        }
        int start = 0;
        for (int i = 0; i < len; i++) {
            set.add(arr[i]);
            if (check(start,i)){
                res++;
                start = i+1;
            }
        }
        return res;
    }

    private boolean check(int start, int end) {
        for (int i = start; i <= end; i++) {
            if (!set.contains(nums.get(i))){
                return false;
            }
        }
        return true;
    }

    @Test
    public void test1() {
        Assertions.assertEquals(maxChunksToSorted(new int[]{4, 3, 2, 1,0 }), 0);
    }
}
