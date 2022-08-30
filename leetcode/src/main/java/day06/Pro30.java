package day06;

import java.util.*;

/**
 * @author qyl
 * @program Pro30.java
 * @Description 串联所有单词的子串
 * @createTime 2022-08-30 12:48
 */
public class Pro30 {
    public static void main(String[] args) {
        System.out.println(new Solution30().findSubstring("wordgoodgoodgoodbestword", new String[]{"word","good","best","good"}));
    }
}

class Solution30 {
    public List<Integer> findSubstring(String s, String[] words) {
        List<Integer> res = new ArrayList<>();
        int lens = s.length(), m = words[0].length(), n = words.length;
        HashMap<String, Integer> wordMap = new HashMap<>(5000);
        HashMap<String, Integer> sMap = new HashMap<>(5000);
        if (n * m > lens) {
            return res;
        }
        for (String word : words) {
            wordMap.put(word, wordMap.getOrDefault(word, 0) + 1);
        }

        for (int i = 0; i + n * m <= lens; i++) {
            sMap.clear();
            for (int j = 0; j < n; j++) {
                String kword = s.substring(i + j * m, i + j * m + m);
                sMap.put(kword, sMap.getOrDefault(kword, 0) + 1);
            }
            if (check(sMap, wordMap)) {
                res.add(i);
            }
        }
        return res;
    }

    private boolean check(HashMap<String, Integer> sMap, HashMap<String, Integer> wordMap) {
        boolean flag = true;
        Iterator<Map.Entry<String, Integer>> iterator = wordMap.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Integer> entry = iterator.next();
            flag = flag & entry.getValue().equals(sMap.getOrDefault(entry.getKey(), -1));
        }
        return flag;
    }
}