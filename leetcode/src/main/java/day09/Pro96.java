package day09;

/**
 * @description 不同的二叉搜索树
 * @date:2022/9/3 22:18
 * @author: qyl
 */
public class Pro96 {
    public static void main(String[] args) {
        System.out.println(new Solution96().numTrees(3));
    }
}

class Solution96 {
    public int numTrees(int n) {
        long ret = 1;
        for (int i = 0; i < n; i++) {
            ret = ret * 2 * (2 * i + 1) /(i + 2);
        }
        return (int)ret;
    }

}
