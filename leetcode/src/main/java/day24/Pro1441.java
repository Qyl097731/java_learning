package day24;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * @description
 * @date:2022/10/15 10:40
 * @author: qyl
 */
public class Pro1441 {
    public static void main(String[] args) {
        System.out.println(new Solution1441().buildArray(new int[]{1, 3}, 3));
        System.out.println(new Solution1441().buildArray(new int[]{1, 2}, 4));
        System.out.println(new Solution1441().buildArray(new int[]{1, 2, 3}, 3));

    }
}

class Solution1441 {
    public List<String> buildArray(int[] target, int n) {
        List<String> res = new ArrayList<>();
        ArrayDeque<Integer> dq = new ArrayDeque<>();
        for (int i = 1; i <= n; i++) {
            dq.addLast(i);
        }
        int idx = 0, len = target.length,diff = 0;
        while (idx < len) {
            if (dq.pollFirst() == target[idx]) {
                idx++;
                while(diff > 0){
                    diff--;res.add("Pop");
                }

            } else {
                diff++;
            }
            res.add("Push");
        }
        return res;
    }
}
