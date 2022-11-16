package day46;

import org.junit.jupiter.api.Test;

/**
 * @date:2022/11/16 16:30
 * @author: qyl
 */
public class Solution2451 {
    int[][] dif = new int[100][20];

    public String oddString(String[] words) {
        int idx = 0;
        int n = words.length;
        for (int i = 0; i < n; i++) {
            System.arraycopy(cal(words[i]), 0, dif[i], 0, 20);
        }

        for (int i = 0; i + 1 < n; i++) {
            if (!check(i, i + 1)) {
                int j = i + 2 < n ? i + 2 : i - 1;
                return check(i, j) ? words[i + 1] : words[i];
            }
        }
        return words[idx];
    }

    private boolean check(int i, int j) {
        for (int k = 0; k < 20; k++) {
            if (dif[i][k] != dif[j][k]) {
                return false;
            }
        }
        return true;
    }

    private int[] cal(String word) {
        int n = word.length();
        int[] temp = new int[20];
        for (int i = 0; i + 1 < n; i++) {
            temp[i] = word.charAt(i + 1) - word.charAt(i);
        }
        return temp;
    }

    @Test
    public void test() {
        String[] str = {"aaa", "bob", "ccc", "ddd"};
        System.out.println(oddString(str));
    }
}
