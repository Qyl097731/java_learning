package day03;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qyl
 * @program Pro76.java
 * @Description 思维题
 * @createTime 2022-08-26 08:57
 */
public class Pro76 {
    public static void main(String[] args) {
        System.out.println(new Solution76().minWindow("a","aa"));
    }

}
class Solution76 {
    HashMap<Character, Integer> tmap = new HashMap<>(52);

    public String minWindow(String s, String t) {
        int n = s.length(), m = t.length();
        for (int i = 0; i < m; i++) {
            Character c = t.charAt(i);
            tmap.put(c, tmap.getOrDefault(c, 0) + 1);
        }

        int left = 0, right = -1,  Rleft = 0, Rright = 0x3f3f3f3f;
        while (left < n && right < n) {
            right++;
            if (right < n) {
                Character c = s.charAt(right);
                if (tmap.containsKey(c)) {
                    tmap.replace(c, tmap.get(c) - 1);
                    while(check() && left <= right) {
                        if (Rright - Rleft > right - left) {
                            Rleft = left;
                            Rright = right;
                        }
                        if (tmap.containsKey(s.charAt(left))){
                            tmap.replace(s.charAt(left),tmap.get(s.charAt(left))+1);
                        }
                        left++;
                    }
                }
            }
        }
        return Rright == 0x3f3f3f3f ? "" :s.substring(Rleft,Rright+1);
    }

    boolean check() {
        boolean flag = true;
        for (Map.Entry<Character, Integer> entry : tmap.entrySet()) {
            flag = entry.getValue() <= 0 && flag;
        }
        return flag;
    }

}
