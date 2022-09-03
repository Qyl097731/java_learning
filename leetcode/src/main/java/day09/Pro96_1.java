package day09;

/**
 * @description
 * @date:2022/9/3 22:47
 * @author: qyl
 */
public class Pro96_1 {
    public static void main(String[] args) {
        System.out.println(new Solution96_1().numTrees(3));
    }
}

class Solution96_1 {
    public int numTrees(int n) {
        int[] ret = new int[n + 1];
        ret[0] = ret[1] = 1;
        for (int i = 2; i <= n; i++) {
            for (int j = 1; j <= i; j++) {
                ret[i] += ret[i - j] * ret[j - 1];
            }
        }
        return ret[n];
    }
}
