package day39;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * @description
 * @date:2022/11/4 18:56
 * @author: qyl
 */
public class Solution136 {
    public int singleNumber(int[] nums) {
        return IntStream.of(nums).reduce((a,b)->a^b).getAsInt();
    }
}
