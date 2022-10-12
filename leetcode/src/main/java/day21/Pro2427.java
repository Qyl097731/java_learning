package day21;

/**
 * @description
 * @date:2022/10/12 20:35
 * @author: qyl
 */
class Solution2427 {
    public int commonFactors(int a, int b) {
        int res =0 ;

        if (a > b){
            int temp = a;
            a = b;
            b=temp;
        }
        for (int i = 1; i <= a; i++) {
            if (b % i == 0 && a % i == 0){
                res++;
            }
        }
        return res;
    }
}
