package daily;

/**
 * @description
 * @date 2023/3/17 23:44
 * @author: qyl
 */
public class Offer64 {
    public int sumNums(int n) {
        int m = n + 1;
        int result = 0;
        while (n != 0) {
            if ((n & 1) == 1) {
                result += m;
            }
            n>>=1;
            m += m;
        }
        return result >> 1;
    }
}
