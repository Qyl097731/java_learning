package concurrent;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * @description
 * @date:2023/1/18 21:11
 * @author: qyl
 */
public class AtomicIntegerArrayTest {

    public static void main(String[] args) {
        int[] nums = new int[]{1,2,3};
        System.out.println (Arrays.equals (nums, nums.clone ()));
        AtomicIntegerArray i = new AtomicIntegerArray (nums);
        int num = i.getAndIncrement (0);
        System.out.println (Arrays.toString (nums) + " i " + i );
        nums[0] = 1;
        System.out.println (Arrays.toString (nums) + " i " + i );
    }
}
