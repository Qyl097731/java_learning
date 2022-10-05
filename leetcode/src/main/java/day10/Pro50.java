package day10;

/**
 * @description
 * @date:2022/9/6 23:40
 * @author: qyl
 */
public class Pro50 {
    public static void main(String[] args) {
        System.out.println(new Solution50().myPow(2.0000, -2147483648));
    }
}

class Solution50 {
    public double myPow(double x, int n) {
        double res = 1.0000;
        boolean flag = n > 0;

        long n1 = n > 0 ? n : -1L*n;
        while (n1 > 0) {
            if ((n1 & 1) == 1) {
                res *= x;
            }
            n1 >>= 1;
            x *= x;
        }
        return flag ? res : 1.0 / res;
    }
}
