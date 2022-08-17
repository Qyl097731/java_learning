package leetcode;

import java.util.*;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 01:41
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */

class Solution17 {
    static List<String> res = new ArrayList<>();
    static Map<Character,String> map = new HashMap<Character, String>(){{
        put('2', "abc");
        put('3', "def");
        put('4', "ghi");
        put('5', "jkl");
        put('6', "mno");
        put('7', "pqrs");
        put('8', "tuv");
        put('9', "wxyz");
    }};
    public List<String> letterCombinations(String digits) {
        res.clear();

        int len = digits.length();
        if(len == 0){
            return res;
        }
        dfs(0,len,new StringBuffer(),digits);

        return res;
    }

    static void dfs(int count,int len,StringBuffer str,String digits){
        if(count == len){
            res.add(str.toString());
            return;
        }
        String temp = map.get(digits.charAt(count));
        int tempLen = temp.length();
        for(int i = 0 ; i < tempLen ; i++){
            str.append(temp.charAt(i));
            dfs(count+1,len,str,digits);
            str.deleteCharAt(count);
        }
    }
}
