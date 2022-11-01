package day38;

import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @description
 * @date:2022/11/1 19:00
 * @author: qyl
 */
public class Solution128 {
    int res = 0, pre, cur, temp = 1;

    public int longestConsecutive(int[] nums) {
        Set<Integer> set = new HashSet<>();
        for (int num : nums) {
            set.add(num);
        }
        Iterator<Integer> iterator = set.iterator();
        if(iterator.hasNext()) pre = iterator.next();
        else return res;
        while (iterator.hasNext()){
            cur = iterator.next();
            if (cur - pre == 1){
                temp++;
            }else {
                res = Math.max(res,temp);
                temp = 1;
            }
            pre = cur;
        }
        return Math.max(res,temp);
    }

    @Test
    public void test() {
        longestConsecutive(new int[]{0,3,7,2,5,8,4,6,0,1});
    }
}
