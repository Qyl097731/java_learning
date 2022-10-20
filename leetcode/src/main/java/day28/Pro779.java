package day28;

/**
 * @description
 * @date:2022/10/20 13:39
 * @author: qyl
 */
public class Pro779 {
    public static void main(String[] args) {
        System.out.println(new Solution779().kthGrammar(3, 3));
    }
}
class Solution779 {
    public int kthGrammar(int n, int k) {
        if (n == 1){
            return 0;
        }
        if (k > (1<<(n-2))){
            return 1^kthGrammar(n-1,k-(1<<(n-2)));
        }else{
            return kthGrammar(n-1,k);
        }
    }
}
