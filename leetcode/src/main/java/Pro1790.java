import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/10/11 0:02
 * @author: qyl
 */
class Solution1790 {
    public boolean areAlmostEqual(String s1, String s2) {
        int len1 = s1.length(), dif = 0;
        for (int i = 0; i < len1; i++) {
            if (s1.charAt(i) != s2.charAt(i)) {
                dif++;
                for (int j = i + 1; j < len1; j++) {
                    if (s1.charAt(j) != s2.charAt(j)) {
                        dif++;
                        if (s1.charAt(j) != s2.charAt(i) || s1.charAt(i) != s2.charAt(j)) {
                            return false;
                        }
                        while (++j < len1) {
                            if (s1.charAt(j) != s2.charAt(j)) {
                                return false;
                            }
                        }
                    }
                }
                break;
            }
        }
        return dif == 2 || dif == 0;
    }

    @Test
    public void test1() {
        Assertions.assertTrue(areAlmostEqual("bank", "kanb"));
        Assertions.assertTrue(areAlmostEqual("kelb", "kelb"));
        Assertions.assertFalse(areAlmostEqual("attack", "defend"));
        Assertions.assertFalse(areAlmostEqual("abcd", "dcba"));
        Assertions.assertFalse(areAlmostEqual("aa", "ac"));
        Assertions.assertFalse(areAlmostEqual("a", "c"));


    }
}
