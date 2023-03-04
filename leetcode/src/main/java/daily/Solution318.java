package daily;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

/**
 * @description
 * @date 2023/3/4 15:02
 * @author: qyl
 */
public class Solution318 {
    public int maxProduct(String[] words) {
        int length = words.length;
        int[][] records = new int[length][2];
        for (int i = 0; i < length; i++) {
            String word = words[i];
            // 记录字符串长度
            records[i][0] = word.length ();
            for (int j = 0; j < records[i][0]; j++) {
                int move = word.charAt (j) - 'a';
                // | 或运算呀 把出现的字母位置标记 1
                // 如 000 如果出现了'a' 现在就是 001； 出现b 就是010
                records[i][1] |= (1 << move);
            }
        }

        int result = 0;
        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                if (i != j) {
                    if ((records[i][1] & records[j][1]) == 0) {
                        result = Math.max (result, records[i][0] * records[j][0]);
                    }
                }
            }
        }
        return result;
    }

    @Test
    public void test() {
        Assertions.assertEquals (maxProduct (new String[]{"abcw", "baz", "foo", "bar", "xtfn", "abcdef"}), 16);
    }
}
