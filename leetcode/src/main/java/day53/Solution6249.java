package day53;

/**
 * @description
 * @date:2022/11/26 22:19
 * @author: qyl
 */
public class Solution6249 {
    public int numberOfCuts(int n) {
        if (n == 1) {
            return 0;
        }
        return n % 2 == 0 ? n / 2 : n;
    }
}
