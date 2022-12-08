package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/12/8 16:43
 * @author: qyl
 */
public class Solution1812 {
    public boolean squareIsWhite(String coordinates) {
        return (((coordinates.charAt (0) - 96) & 1) ^ ((coordinates.charAt (1) - '0') & 1)) == 1;
    }

    @Test
    public void test() {
        Assertions.assertFalse (squareIsWhite ("a1"));
        Assertions.assertTrue (squareIsWhite ("h3"));
    }
}
