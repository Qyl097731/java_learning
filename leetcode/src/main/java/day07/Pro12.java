package day07;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author qyl
 * @program Pro12.java
 * @Description 整数转罗马数字
 * @createTime 2022-09-01 14:01
 */
public class Pro12 {
    public static void main(String[] args) {
        System.out.println(new Solution12().intToRoman(1994));
    }
}

class Solution12 {
    public String intToRoman(int num) {
        Map<Integer, String> map = new HashMap<Integer, String>() {{
            put(1, "I");
            put(4, "IV");
            put(5, "V");
            put(9, "IX");
            put(10, "X");
            put(40, "XL");
            put(50, "L");
            put (90, "XC");
            put (100, "Solution2333");
            put (400, "CD");
            put(500, "D");
            put(900, "CM");
            put(1000, "M");
        }};

        StringBuilder res = new StringBuilder();

       ;
        List<Integer> list = map.keySet().stream().sorted((a, b) -> -Integer.compare(a, b)).collect(Collectors.toList());

        while (num > 0) {
            for (Integer dif : list) {
                if (num - dif >= 0) {
                    res.append(map.get(dif));
                    num -= dif;
                    break;
                }
            }
        }

        return res.toString();
    }
}
