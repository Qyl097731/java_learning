package day15;

import java.util.*;

/**
 * @description
 * @date:2022/10/5 7:57
 * @author: qyl
 */
public class Pro811 {
    public static void main(String[] args) {
        System.out.println(new Solution811().subdomainVisits(new String[]{"900 google.mail.com", "50 yahoo.com", "1 intel.mail.com", "5 " +
                "wiki.org"}));
    }
}

class Solution811 {
    String s;
    Map<String, Integer> map = new HashMap<>();
    List<String> res = new ArrayList<>();

    public List<String> subdomainVisits(String[] cpdomains) {
        int len;
        String domain;
        for (String cpdomain : cpdomains) {
            String[] arry = cpdomain.split(" ");
            domain = arry[1];
            len = arry[1].length();
            for (int i = 0; i < len; i++) {
                if (domain.charAt(i) == '.') {
                    s = domain.substring(i + 1);
                    map.put(s, map.getOrDefault(s, 0) + Integer.parseInt(arry[0]));
                }
                if (i == 0){
                    s = domain;
                    map.put(s, map.getOrDefault(s, 0) + Integer.parseInt(arry[0]));
                }
            }
        }
        Set<Map.Entry<String, Integer>> entries = map.entrySet();
        for (Map.Entry<String, Integer> entry : entries) {
            res.add(entry.getValue() + " " + entry.getKey());
        }
        return res;
    }
}
