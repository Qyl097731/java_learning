package day46;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @date:2022/11/16 19:19
 * @author: qyl
 */
public class Solution2452 {
    List<String> res = new ArrayList<>();

    public List<String> twoEditWords(String[] queries, String[] dictionary) {
        for (String query : queries) {
            for (String dic : dictionary) {
                if (check(query, dic)) {
                    res.add(query);
                    break;
                }
            }
        }
        return res;
    }

    private boolean check(String query, String dic) {
        int n = query.length();
        int dif = 0;
        for (int i = 0; i < n; i++) {
            dif += query.charAt(i) == dic.charAt(i) ? 1 : 0;
        }
        return dif <= 2;
    }
}
