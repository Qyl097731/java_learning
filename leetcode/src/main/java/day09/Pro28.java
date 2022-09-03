package day09;

/**
 * @description
 * @date:2022/9/3 22:58
 * @author: qyl
 */
public class Pro28 {
    public static void main(String[] args) {
        System.out.println(new Solution28().strStr("aaaaa", ""));
    }
}
class Solution28 {
    public int strStr(String haystack, String needle) {
        return haystack.indexOf(needle);
    }
}
