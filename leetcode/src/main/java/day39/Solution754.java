package day39;

/**
 * @description
 * @date:2022/11/3 14:41
 * @author: qyl
 */
public class Solution754 {
    public int reachNumber(int target) {
        int step = 0,sum = 0;
        target = Math.abs(target);
        while (sum < target || ((sum - target) & 1) == 1){
            sum += ++step;
        }
        return step;
    }
}
