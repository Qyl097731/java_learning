package leetcode;

import java.util.ArrayList;
import java.util.List;

/**
 * projectName:  fushi
 * packageName: leetcode
 * date: 2022-03-27 22:16
 * copyright(c) 2020 南晓18卓工 邱依良
 *
 * @author 邱依良
 */
public class Pro22 {
    public static void main(String[] args) {
        List<String> strings = new Solution22().generateParenthesis(3);
        for (String s:strings){
            System.out.println(s);
        }
    }
}
class Solution22 {

    int len ;
    public List<String> generateParenthesis(int n) {
        List<String>res = new ArrayList<>();
        len = n;
        dfs(res,0,0,new StringBuffer());
        return res;
    }
    void dfs(List<String>res,int l, int r,StringBuffer str){
        if(str.length() == len*2){
            res.add(str.toString());
            return ;
        }
        for(int i = l+1 ; i <= len ; i++){
            str.append('(');
            dfs(res,i,r,str);
            str.deleteCharAt(str.length()-1);
        }
        for(int i = r+1 ; i <= l ; i++){
            str.append(')');
            dfs(res,l,i,str);
            str.deleteCharAt(str.length()-1);
        }

    }
}
