package day06;

/**
 * @author qyl
 * @program Pro8.java
 * @Description 字符串转换整数 (atoi)
 * @createTime 2022-08-30 11:53
 */
public class Pro8 {
    public static void main(String[] args) {
        System.out.println(new Solution8().myAtoi("  +-12"));
    }
}

class Solution8 {
    public int myAtoi(String s) {
        long res = 0;
        long MAX = Integer.MAX_VALUE, MIN = Integer.MIN_VALUE;

        s = s.trim();
        int len = s.length(), flag = 1;

        for (int i = 0; i < len; i++) {
            if (i == 0){
                if (Character.isDigit(s.charAt(i))){
                    flag = 1;
                    res = s.charAt(i)-'0';
                }else if (s.charAt(i) == '-'){
                    flag = -1;
                }else if(s.charAt(i) == '+') {
                    flag = 1;
                }else {
                    break;
                }
                continue;
            }

            if (!Character.isDigit(s.charAt(i))) {
                break;
            }

            res = res * 10 + s.charAt(i)-'0' ;
            if (flag > 0 && res > MAX) {
                return (int) MAX;
            }
            if (flag < 0 && res * flag < MIN) {
                return (int) MIN;
            }
        }
        return (int) res * flag ;
    }
}