package leetcode;

import java.util.*;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-28 17:45
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro49 {
    public static void main(String[] args) {
        new Solution49().groupAnagrams(new String[]{"eat", "tea", "tan", "ate", "nat", "bat"});
    }
}
class Solution49 {
    public List<List<String>> groupAnagrams(String[] strs) {
        int len = strs.length ;

        Map<String,List<String>> map = new HashMap<>();
        for(int i = 0 ; i < len;i++){
            String s = strs[i],str = s;

            char[] scopy = str.toCharArray();
            Arrays.sort(scopy);
            str = new String(scopy);


            List<String>strings = map.getOrDefault(str,new ArrayList<>());
            strings.add(s);
            map.put(str,strings);
        }
        return  new ArrayList<>(map.values());
    }
}
