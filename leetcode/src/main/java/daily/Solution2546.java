package daily;

/**
 * @description
 * @date:2023/1/26 0:06
 * @author: qyl
 */
public class Solution2546 {
    public boolean makeStringsEqual(String s, String target) {
        return s.contains ("1") == target.contains ("1");
    }
}
