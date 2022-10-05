package day11;

/**
 * @description
 * @date:2022/9/28 13:18
 * @author: qyl
 */
public class Pro38 {
    public static void main(String[] args) {
        System.out.println(new Solution38().countAndSay(4));
    }
}
class Solution38 {
    public String countAndSay(int n) {
        StringBuilder s = new StringBuilder("1#");
        for (int i = 2; i <= n; i++) {
            StringBuilder newStr = new StringBuilder();

            for (int j = 0; j < s.length()-1; j++) {
                char c = s.charAt(j);
                int num = 1;
                while(s.charAt(j) == s.charAt(j+1)){
                    j++;num++;
                }
                newStr.append(num).append(c);
            }
            s = newStr.append("#");
        }
        return s.substring(0,s.length()-1);
    }
}
