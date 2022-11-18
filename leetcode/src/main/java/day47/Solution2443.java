package day47;

import org.junit.jupiter.api.Test;

/**
 * @description
 * @date:2022/11/18 1:50
 * @author: qyl
 */
public class Solution2443 {
    boolean flag = false;

    public boolean sumOfNumberAndReverse(int num) {
        if (num == 0) {
            return true;
        }
        int cnt = 0, temp = num;
        while (temp > 0) {
            cnt++;
            temp /= 10;
        }
        dfs(new StringBuilder(), 0, cnt, num);
        return flag;
    }

    private void dfs(StringBuilder temp, int cur, int cnt, int num) {
        if (flag) {
            return;
        }
        if (cnt == cur) {
            flag = (temp.charAt(0) != '0' || temp.charAt(cur - 1) != '0') &&
                    num == Integer.parseInt(temp.toString()) + Integer.parseInt(temp.reverse().toString());
            return;
        }
        if (cur > 0 && cnt == cur + 1) {
            flag = (temp.charAt(0) != '0' || temp.charAt(cur - 1) != '0') &&
                    (num == Integer.parseInt(temp.toString()) + Integer.parseInt(temp.reverse().toString()));
        }
        if (flag) {
            return;
        }
        for (int i = 0; i < 10 && !flag; i++) {
            temp.append(i);
            dfs(temp, cur + 1, cnt, num);
            temp.deleteCharAt(cur);
        }
    }

    @Test
    public void test() {
        System.out.println(sumOfNumberAndReverse(187));
    }
}
