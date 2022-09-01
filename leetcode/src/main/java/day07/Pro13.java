package day07;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qyl
 * @program Pro13.java
 * @Description 罗马数字转整数
 * @createTime 2022-09-01 13:31
 */
public class Pro13 {
    public static void main(String[] args) {
        System.out.println(new Solution13().romanToInt("DMXXLIIV"));
    }
}

class Solution13 {
    public int romanToInt(String s) {
        Map<Character, Integer> map = new HashMap<Character, Integer>() {{
            put('I', 1);
            put('V', 5);
            put('X', 10);
            put('L', 50);
            put('C', 100);
            put('D', 500);
            put('M', 1000);
            put('Z', 10000);

        }};
        int res = 0, temp = 0, len = s.length() + 1, last = 0;

        s = "Z".concat(s);
        for (int i = 1; i < len; ) {
            temp = map.get(s.charAt(i++));
            while (i < len && s.charAt(i) == s.charAt(i - 1)) {
                temp += map.get(s.charAt(i++));
            }
            if (temp > last && last != 0) {
                res += (temp - 2 * last);
            }else{
                res += temp;
            }
            last = temp;
        }
        return res;
    }
}