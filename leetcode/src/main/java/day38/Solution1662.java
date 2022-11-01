package day38;

/**
 * @description
 * @date:2022/11/1 16:31
 * @author: qyl
 */
public class Solution1662 {
    public boolean arrayStringsAreEqual(String[] word1, String[] word2) {
        return String.join("",word1).equals(String.join("",word2));
    }
}
