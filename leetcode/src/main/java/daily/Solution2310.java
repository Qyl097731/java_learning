package daily;

/**
 * @description
 * @date:2022/12/12 10:56
 * @author: qyl
 */
public class Solution2310 {
    public int minimumNumbers(int num, int k) {
        if (num == 0) {
            return 0;
        }
        if (num % 10 == k) {
            return 1;
        }
        int res = num + 1;
        for (int i = 2; i <= num; i++) {
            int dif = k * i, temp;
            if (dif > num) {
                break;
            }
            temp = num - dif;
            if (temp % 10 == 0) {
                return i;
            }
        }
        return res == num + 1 ? -1 : res;
    }
}
